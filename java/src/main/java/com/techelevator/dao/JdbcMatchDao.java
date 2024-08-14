package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.Match;
import com.techelevator.model.MatchDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcMatchDao implements MatchDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcMatchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Match> getMatches(){
        List<Match> matches = new ArrayList<>();
        String sql = "SELECT * FROM match";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Match match = mapRowToMatch(results);
                matches.add(match);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return matches;
    }

    @Override
    public List<Match> getMatchesByPlayerId(int playerId) {
        String sql = "SELECT * FROM match WHERE player1_id = ? or player2_id = ?";
        List<Match> matches = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,playerId,playerId);
            while (results.next()) {
                Match match = mapRowToMatch(results);
                matches.add(match);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return matches;
    }

    @Override
    public List<Match> getMatchesByTournamentId(int tournamentId) {
        String sql = "SELECT m.match_id,m.game_id,m.is_scrim,m.player1_id,m.player2_id,m.player1_score,m.player2_score,m.winner_id,m.is_draw \n" +
                "FROM match m\n" +
                "JOIN tournament_players tp \n" +
                "ON m.player1_id = tp.user_id\n" +
                "JOIN tournament t \n" +
                "on tp.tournament_id = t.tournament_id\n" +
                "WHERE t.tournament_id = ? ";
        List<Match> matches = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,tournamentId);
            while (results.next()) {
                Match match = mapRowToMatch(results);
                matches.add(match);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return matches;
    }

    @Override
    public Match getMatchById(int matchId){
        Match match;
        String sql = "SELECT * FROM match WHERE match_id = ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, matchId);
            match = (results.next()) ? mapRowToMatch(results) : null;

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return match;
    }

    @Override
    public Match createMatch(Match match) {
        Match newMatch;
        String sql = "INSERT INTO match(game_id, is_scrim, player1_id, player2_id, player1_score, player2_score, winner_id, is_draw,match_start_time) VALUES " +
                     "(?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING match_id;";

        try {
            int matchId = jdbcTemplate.queryForObject(sql, int.class,
                    match.getGameId(),
                    match.getIsScrim(),
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    match.getPlayer1Score(),
                    match.getPlayer2Score(),
                    match.getWinnerId(),
                    match.getIsDraw(),
                    match.getMatchStartTime()
            );
            newMatch = getMatchById(matchId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return newMatch;
    }
    @Override
    public int updateMatch(MatchDto match){
        int rowsAffected = 0;
        Match updateMatch = null;
        String sql = "UPDATE match " +
                "SET player1_score = ?, player2_score = ?, winner_id = ?, is_draw = ?, " +
                "match_start_time = ? " +
                "WHERE match_id = ?";

        try{
            Integer matchId = match.getMatchId();
            matchId = matchId == 0 ? null : matchId;

            rowsAffected = jdbcTemplate.update(sql,
                    match.getPlayer1Score(),
                    match.getPlayer2Score(),
                    match.getWinnerId(),
                    match.getIsDraw(),
                    match.getMatchStartTime(),
                    match.getMatchId()
                    );

        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database",e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation",e);
        }
        return rowsAffected;
    }

    public Match mapRowToMatch(SqlRowSet result){
        Match match = new Match();
        match.setMatchId(result.getInt("match_id"));
        match.setGameId(result.getInt("game_id"));
        match.setScrim(result.getBoolean("is_scrim"));
        match.setIsDraw(result.getBoolean("is_draw"));
        match.setPlayer1Id(result.getInt("player1_id"));
        match.setPlayer2Id(result.getInt("player2_id"));
        match.setPlayer1Score(result.getDouble("player1_score"));
        match.setPlayer2Score(result.getDouble("player2_score"));
        match.setWinnerId(result.getInt("winner_id"));


        return match;
    }



}
