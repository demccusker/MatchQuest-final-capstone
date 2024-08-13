package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.Match;
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
    public Match getMatchById(int matchId){
        Match match = null;
        String sql = "SELECT * FROM match WHERE match_id = ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, matchId);
            if(results.next()){
                match = mapRowToMatch(results);

            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return match;
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

    public Match mapRowToMatch(SqlRowSet result){
        Match match = new Match();
        match.setMatchId(result.getInt("match_id"));
        match.setGameId(result.getInt("game_id"));
        match.setScrim(result.getBoolean("is_scrim"));
        match.setDraw(result.getBoolean("is_draw"));
        match.setPlayer1Id(result.getInt("player1_id"));
        match.setPlayer2Id(result.getInt("player2_id"));
        match.setPlayer1Score(result.getDouble("player1_score"));
        match.setPlayer2Score(result.getDouble("player2_score"));
        match.setWinnerId(result.getInt("winner_id"));


        return match;
    }



}
