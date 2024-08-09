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

    public Match mapRowToMatch(SqlRowSet result){
        Match match = new Match();
        match.setMatchId(result.getInt("match_id"));
        match.setGameId(result.getInt("game_id"));
        match.setIsScrim(result.getBoolean("is_scrim"));

        return match;
    }



}
