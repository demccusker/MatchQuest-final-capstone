package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Tournament;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public class JdbcTournamentDao implements TournamentDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTournamentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tournament> getAllTournaments(){
        return null;

    }

    public Tournament getTournamentById(int tournamentId) {
        String sql = "SELECT * FROM tournament WHERE tournament_id = ? ";
        Tournament tournament = null;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,tournamentId);
            tournament = (results.next()) ? mapRowToTournament(results)  : null;

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return tournament;

    }
    @Override
    public Tournament createTournament(Tournament tournament){
        Tournament newTournament = null;

        String sql = "INSERT INTO tournament (game_id,bracket_id, creator_id, name,is_scrim)\n" +
                "VALUES (?, ?, ?, ?, ? ) RETURNING tournament_id";
        try {
            int newTournamentId = jdbcTemplate.queryForObject(sql,int.class,tournament.getGameId(),tournament.getBracketId(),
                    tournament.getCreatorId(),tournament.getName(),tournament.getIsScrim());
            newTournament = getTournamentById(newTournamentId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTournament;
    }

    public Tournament updateTournament(Tournament tournament){
        return null;
    }

    private Tournament mapRowToTournament(SqlRowSet result) {
        Tournament tournament = new Tournament();
        tournament.setBracketId(result.getInt("bracket_id"));
        tournament.setGameId(result.getInt("game_id"));
        tournament.setCreatorId(result.getInt("creator_id"));
        tournament.setName(result.getString("name"));
        tournament.setIsScrim(result.getBoolean("is_scrim"));
        return tournament;
    }
}
