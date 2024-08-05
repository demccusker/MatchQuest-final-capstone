package com.techelevator.dao;

import com.techelevator.model.Tournament;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTournamentDao implements TournamentDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTournamentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Tournament createTournament(){
        Tournament newTournament = null;

        String sql = "";
        return newTournament;
    }
}
