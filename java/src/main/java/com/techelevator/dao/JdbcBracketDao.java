package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Bracket;
import com.techelevator.utils.ExtraMaths;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcBracketDao implements BracketDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String WHERE_BRACKET_IS_PARENT = "WHERE bracket.bracket_id IN (SELECT parent_bracket FROM bracket WHERE bracket_id = ?) ";
    private static final String WHERE_ = "WHERE bracket.bracket_id IN (SELECT parent_bracket FROM bracket WHERE bracket_id = ?) ";
    /*private static final String RECURSIVE_TREE_SELECT = String.format(
                "WITH RECURSIVE bracket_tree(root, bracket_id, parent_bracket , match_id, name) AS ( " +
                        "SELECT bracket_id AS root, " +
                        "bracket_id, " +
                        "parent_bracket, " +
                        "match_id, " +
                        "name " +
                        "FROM bracket " +
                        "%s" +
                    "UNION " +
                    "SELECT bracket_tree.root, " +
                        "bracket.bracket_id, " +
                        "bracket.parent_bracket, " +
                        "bracket.match_id, " +
                        "bracket.name " +
                        "FROM bracket_tree, bracket " +
                        "WHERE bracket_tree.parent_bracket = bracket.bracket_id " +
                ") " +
                "SELECT bracket_id, parent_bracket, match_id, name FROM bracket_tree " +
                "ORDER BY bracket_id;"
            );*/


    public JdbcBracketDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Bracket> createBracketTree(int numberOfMatches) {
        StringBuilder bracketBuilder = new StringBuilder("INSERT INTO bracket (bracket_id, parent_bracket, match_id, name) VALUES ");
        SqlRowSet row;

        try {
            row = jdbcTemplate.queryForRowSet("SELECT last_value AS id FROM seq_bracket_id");
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        int rootId = (row.next()) ? row.getInt("id") + 255 : 0;

        for (int i = 0, j = 0; i < numberOfMatches; i++) {
            int currentHeight = (int)Math.floor((ExtraMaths.log2(i + 1) + 1));
            String bracketName;

            switch (currentHeight) {
                case 1:
                    bracketName = "Finalist";
                    break;
                case 2:
                    bracketName = "Semi-Finalist";
                    break;
                case 3:
                    bracketName = "Quarter-Finalist";
                    break;
                default:
                    bracketName = "Qualifiers";
                    break;
            }

            bracketBuilder.append(String.format(
                    "(%1$d, %2$d, null, '%3$s')",
                    rootId + i,
                    (i == 0) ? null : rootId + j,
                    bracketName
            ));

            if (i != numberOfMatches - 1) bracketBuilder.append(", ");
            j += (i % 2 == 0 && i != 0) ? 1 : 0;
        }

        bracketBuilder.append(';');

        try {
            int rowsInserted = jdbcTemplate.update(bracketBuilder.toString());
            if (rowsInserted == numberOfMatches)
                jdbcTemplate.execute("SELECT nextval('seq_bracket_id')");
            else
                throw new DaoException("Inserted incomplete or mal-formed bracket tree!");

        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database",e);
        }   catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation",e);
        }

        return getBracketsIdOrder(rootId);
    }

    @Override
    public List<Bracket> getBracketsFromRoot(int bracketId) {
        List<Bracket> tree = new ArrayList<>();
        String sql = "WITH RECURSIVE bracket_tree(root, bracket_id, parent_bracket , match_id, name, sort) AS ( " +
                "SELECT bracket_id AS root, " +
                    "bracket_id, " +
                    "parent_bracket, " +
                    "match_id, " +
                    "name, " +
                    "CAST(bracket_id AS varchar) " +
                    "FROM bracket " +
                    "WHERE bracket_id = ? " +
                "UNION " +
                "SELECT bracket_tree.root, " +
                    "bracket.bracket_id, " +
                    "bracket.parent_bracket, " +
                    "bracket.match_id, " +
                    "bracket.name, " +
                    "bracket_tree.sort || ':' || bracket.bracket_id " +
                    "FROM bracket " +
                    "JOIN bracket_tree " +
                    "ON bracket.parent_bracket = bracket_tree.bracket_id " +
                ") " +
                "SELECT bracket_id, parent_bracket, match_id, name FROM bracket_tree " +
                "ORDER BY sort;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bracketId);
            if (results.next()) {
                mapRecursiveTree(results, null, tree);
            }

        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return tree;
    }

    @Override
    public List<Bracket> getBracketsIdOrder(int bracketId) {
        List<Bracket> tree = new ArrayList<>();
        String sql = "WITH RECURSIVE bracket_tree(root, bracket_id, parent_bracket , match_id, name) AS ( " +
                "SELECT bracket_id AS root, " +
                    "bracket_id, " +
                    "parent_bracket, " +
                    "match_id, " +
                    "name " +
                    "FROM bracket " +
                    "WHERE bracket_id = ? " +
                "UNION " +
                "SELECT bracket_tree.root, " +
                    "bracket.bracket_id, " +
                    "bracket.parent_bracket, " +
                    "bracket.match_id, " +
                    "bracket.name " +
                    "FROM bracket " +
                    "JOIN bracket_tree " +
                    "ON bracket.parent_bracket = bracket_tree.bracket_id " +
                ") " +
                "SELECT bracket_id, parent_bracket, match_id, name FROM bracket_tree;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bracketId);
            while (results.next()) {
                tree.add(mapRowsetToBracket(results));
            }

        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return tree;
    }

    @Override
    public List<Bracket> getAncestors(int bracketId) {
        List<Bracket> tree = new ArrayList<>();
        String sql = "WITH RECURSIVE bracket_tree(root, bracket_id, parent_bracket , match_id, name) AS ( " +
                "SELECT bracket_id AS root, " +
                    "bracket_id, " +
                    "parent_bracket, " +
                    "match_id, " +
                    "name " +
                    "FROM bracket " +
                    "WHERE bracket.bracket_id IN (SELECT parent_bracket FROM bracket WHERE bracket_id = ?) " +
                "UNION " +
                "SELECT bracket_tree.root, " +
                    "bracket.bracket_id, " +
                    "bracket.parent_bracket, " +
                    "bracket.match_id, " +
                    "bracket.name " +
                    "FROM bracket_tree, bracket " +
                    "WHERE bracket_tree.parent_bracket = bracket.bracket_id " +
                ") " +
                "SELECT bracket_id, parent_bracket, match_id, name FROM bracket_tree " +
                "ORDER BY bracket_id;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bracketId);
            if (results.next()) {
                mapRecursiveTree(results, null, tree);
            }

        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return tree;
    }

    @Override
    public List<Bracket> getChildBrackets(int bracketId) {
        List<Bracket> tree = new ArrayList<>();
        String sql = "WITH RECURSIVE bracket_tree(root, bracket_id, parent_bracket , match_id, name) AS ( " +
                "SELECT bracket_id AS root, " +
                    "bracket_id, " +
                    "parent_bracket, " +
                    "match_id, " +
                    "name " +
                    "FROM bracket " +
                    "WHERE bracket_id = ? " +
                "UNION " +
                "SELECT bracket_tree.root, " +
                    "bracket.bracket_id, " +
                    "bracket.parent_bracket, " +
                    "bracket.match_id, " +
                    "bracket.name " +
                    "FROM bracket_tree, bracket " +
                    "WHERE bracket.parent_bracket = bracket_tree.bracket_id " +
                ") " +
                "SELECT bracket_id, parent_bracket, match_id, name FROM bracket_tree " +
                "WHERE parent_bracket = ? OR bracket_id = ?" +
                "ORDER BY bracket_id;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bracketId, bracketId, bracketId);
            if (results.next()) {
                mapRecursiveTree(results, null, tree);
                tree.remove(0);
            }

        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return tree;
    }

    @Override
    public Bracket getBracketByMatchId(int matchId) {
        Bracket bracket;
        String sql = "SELECT bracket_id, parent_bracket, b.match_id, name FROM bracket b " +
                     "JOIN match m ON b.match_id = m.match_id " +
                     "WHERE b.match_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, matchId);
            bracket = (results.next()) ? mapRowsetToBracket(results) : null;

        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return bracket;
    }

    @Override
    public void updateBrackets(List<Bracket> brackets) {
        String sql = "UPDATE bracket " +
                     "SET match_id = ? " +
                     "WHERE bracket_id = ?;";

        try {
            int rowsAffected;
            for(Bracket bracket : brackets) {
                rowsAffected = jdbcTemplate.update(sql, bracket.getMatchId(), bracket.getBracketId());

                if (rowsAffected != 1) throw new DaoException("Unable to update bracket " + bracket.getBracketId());

            }
        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database",e);
        } catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation",e);
        }


    }

    @Override
    public boolean deleteBracketTree(int rootId) {
        if (rootId % 255 != 0) return false;

        String sql = "WITH RECURSIVE bracket_tree(root, bracket_id, parent_bracket, match_id, name) AS ( " +
                "SELECT " +
                    "bracket_id AS root, " +
                    "bracket_id, " +
                    "parent_bracket, " +
                    "match_id, " +
                    "name " +
                    "FROM bracket " +
                    "WHERE parent_bracket IS NULL AND bracket_id = ? " +
                "UNION " +
                "SELECT " +
                    "bracket_tree.root, " +
                    "bracket.bracket_id, " +
                    "bracket.parent_bracket, " +
                    "bracket.match_id, " +
                    "bracket.name " +
                    "FROM bracket " +
                    "JOIN bracket_tree " +
                    "ON bracket.parent_bracket = bracket_tree.bracket_id " +
                ") " +
                "DELETE FROM bracket WHERE bracket_id IN (SELECT bracket_id FROM bracket_tree);";

        try {
            int rowsAffected = jdbcTemplate.update(sql, rootId);
            return rowsAffected != 0;

        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data integrity violation", ex);
        }
    }

    private Bracket mapRecursiveTree(SqlRowSet results, Bracket parent, List<Bracket> tree) {
        tree = (tree == null) ? new ArrayList<>() : tree;
        Bracket bracket = new Bracket();

        int currentBracketId = results.getInt("bracket_id"),
            parentBracketId = results.getInt("parent_bracket");
        boolean isRoot = parentBracketId == 0;

        bracket.setBracketId(currentBracketId);
        bracket.setMatchId(results.getInt("match_id"));
        bracket.setName(results.getString("name"));

        if (!isRoot) {
            bracket.setParentId(parentBracketId);
            bracket.setParentBracket(parent);
        }

        tree.add(bracket);

        /* If there are available nodes traverse */
        while (results.next()) {
            /* We need to check if next nodes parents is current node */
            if (results.getInt("parent_bracket") == currentBracketId) {
                mapRecursiveTree(results, bracket, tree);
            } else {
                results.previous();
                return bracket;
            }
        }

        results.last();
        return bracket;
    }

    private Bracket mapRowsetToBracket(SqlRowSet result) {
        Bracket newBracket = new Bracket();

        newBracket.setBracketId(result.getInt("bracket_id"));
        newBracket.setParentId(result.getInt("parent_bracket"));
        newBracket.setMatchId(result.getInt("match_id"));
        newBracket.setName(result.getString("name"));

        return newBracket;
    }
}
