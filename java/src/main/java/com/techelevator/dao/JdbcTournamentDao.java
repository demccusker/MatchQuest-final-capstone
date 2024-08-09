package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.AddressFilter;
import com.techelevator.model.Tournament;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTournamentDao implements TournamentDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTournamentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tournament> getAllTournaments(){
        List<Tournament> tournaments = new ArrayList<>();
        String sql = "SELECT * FROM tournament";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Tournament tournament = mapRowToTournament(results);
                tournaments.add(tournament);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return tournaments;

    }
    @Override
    public List<Tournament> getActiveTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        String sql = "SELECT * FROM tournament WHERE start_date <= NOW() AND (end_date IS NULL OR end_date >= NOW())";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Tournament tournament = mapRowToTournament(results);
                tournaments.add(tournament);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return tournaments;
    }
    @Override
    public List<Tournament> getPastTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        String sql = "SELECT * FROM tournament WHERE end_date < NOW()";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Tournament tournament = mapRowToTournament(results);
                tournaments.add(tournament);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return tournaments;
    }
    @Override
    public List<Tournament> getTournamentByLocation(AddressFilter addressFilter) {
        List<Tournament> tournaments = new ArrayList<>();
        String baseSql = "SELECT * FROM tournament t JOIN address a ON t.location = a.address_id ";
        String filterClause = addressFilter.getSqlFilterClause("a");


        String sql = baseSql + filterClause;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Tournament tournament = mapRowToTournament(results);
                tournaments.add(tournament);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return tournaments;
    }



    @Override
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
    //WIP needs to be tested
    @Override
    public List<Tournament> getTournamentsByCreatorId(int creatorId){
        String sql = "SELECT * FROM tournament WHERE creator_id = ?";
        List<Tournament> tournaments = new ArrayList<>();
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,creatorId);
            while(results.next()){
                Tournament tournament = mapRowToTournament(results);
                tournaments.add(tournament);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return tournaments;
    }

    @Override
    public Tournament createTournament(Tournament tournament){
        Tournament newTournament;

        String sql = "INSERT INTO tournament (game_id, bracket_id, creator_id, name, is_scrim, is_online, location, start_date, end_date)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? ) RETURNING tournament_id";
        try {
            int newTournamentId = jdbcTemplate.queryForObject(sql,int.class,
                    tournament.getGameId(),tournament.getBracketId(),
                    tournament.getCreatorId(),tournament.getName(),
                    tournament.getIsScrim(),tournament.isOnline(),
                    tournament.getLocation(),
                    tournament.getStartDate(),tournament.getEndDate());
            newTournament = getTournamentById(newTournamentId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTournament;
    }

    public int updateTournament(Tournament tournament){
        int rowsAffected = 0;
        String sql = "UPDATE tournament " +
                "SET game_id = ? , bracket_id = ?, creator_id = ?, name = ?, " +
                "is_scrim = ?, is_online = ?, location = ?, start_date=? , end_date = ? " +
                "WHERE tournament_id = ? ";
        try { rowsAffected = jdbcTemplate.update(sql,tournament.getGameId(),tournament.getBracketId(),
                tournament.getCreatorId(),tournament.getName(),tournament.getIsScrim(),
                tournament.isOnline(),tournament.getLocation(),tournament.getStartDate(),
                tournament.getEndDate(),tournament.getTournamentId());

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowsAffected;
    }

    private Tournament mapRowToTournament(SqlRowSet result) {
        Tournament tournament = new Tournament();
        tournament.setTournamentId(result.getInt("tournament_id"));
        tournament.setBracketId(result.getInt("bracket_id"));
        tournament.setGameId(result.getInt("game_id"));
        tournament.setCreatorId(result.getInt("creator_id"));
        tournament.setName(result.getString("name"));
        tournament.setIsScrim(result.getBoolean("is_scrim"));
        tournament.setIsOnline(result.getBoolean("is_online"));
        tournament.setLocation(result.getString("location"));
        tournament.setStartDate(result.getDate("start_date").toLocalDate());
        tournament.setEndDate(result.getDate("end_date") != null ? result.getDate("end_date").toLocalDate() : null);
        return tournament;
    }
}
