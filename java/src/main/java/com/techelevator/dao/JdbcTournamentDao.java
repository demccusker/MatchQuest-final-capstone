package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.AddressFilter;
import com.techelevator.model.QueryFilter;
import com.techelevator.model.Tournament;
import com.techelevator.model.UserDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTournamentDao implements TournamentDao{
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_TOURNAMENT = "SELECT tournament_id, game_id, bracket_id, creator_id, name, is_scrim, is_online, location, max_participants, start_date, end_date FROM tournament t ";
    private static final String JOIN_ADDRESS = "JOIN address a ON t.location = a.address_id ";
    private static final String SELECT_TOURNAMENT_ADDRESS_BRIDGE = SELECT_TOURNAMENT + JOIN_ADDRESS;

    public JdbcTournamentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tournament> getAllTournaments(){
        List<Tournament> tournaments = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(SELECT_TOURNAMENT);
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
        String sql = SELECT_TOURNAMENT +
                    "WHERE start_date <= NOW() AND (end_date IS NULL OR end_date >= NOW())";
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
        String sql = SELECT_TOURNAMENT +
                    "WHERE end_date < NOW()";
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
    public List<Tournament> getTournamentsByLocation(AddressFilter addressFilter) {
        List<Tournament> tournaments = new ArrayList<>();
        String sql = SELECT_TOURNAMENT_ADDRESS_BRIDGE + addressFilter.getSqlFilterClause();

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
    public List<Tournament> getTournamentsByFilter(QueryFilter filter) {
        List<Tournament> tournaments = new ArrayList<>();
        String sql = filter.getSqlQuery();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                tournaments.add(mapRowToTournament(results));
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return tournaments;
    }

    @Override
    public Tournament getTournamentById(int tournamentId) {
        String sql = SELECT_TOURNAMENT + "WHERE tournament_id = ? ";
        Tournament tournament;

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

        String sql = "INSERT INTO tournament (game_id, bracket_id, creator_id, name, is_scrim, is_online, location, start_date, end_date,max_participants)\n" +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) RETURNING tournament_id";
        try {
            int newTournamentId = jdbcTemplate.queryForObject(sql, int.class,
                    tournament.getGameId(),
                    tournament.getBracketId(),
                    tournament.getCreatorId(),
                    tournament.getName(),
                    tournament.getIsScrim(),
                    tournament.isOnline(),
                    tournament.getLocation(),
                    tournament.getStartDate(),
                    tournament.getEndDate(),
                    tournament.getMaxParticipants()

            );
            newTournament = getTournamentById(newTournamentId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTournament;
    }

    @Override
    public int updateTournament(Tournament tournament){
        int rowsAffected = 0;
        String sql = "UPDATE tournament " +
                "SET game_id = ? , bracket_id = ?, creator_id = ?, name = ?, " +
                "is_scrim = ?, is_online = ?, location = ?,max_participants = ? , start_date=? , end_date = ? " +
                "WHERE tournament_id = ? ";
        try {
            Integer bracketId = tournament.getBracketId();
            bracketId = (bracketId == 0) ? null : bracketId;

            rowsAffected = jdbcTemplate.update(sql,
                    tournament.getGameId(),
                    bracketId,
                    tournament.getCreatorId(),
                    tournament.getName(),
                    tournament.getIsScrim(),
                    tournament.isOnline(),
                    tournament.getLocation(),
                    tournament.getMaxParticipants(),
                    tournament.getStartDate(),
                    tournament.getEndDate(),
                    tournament.getTournamentId()
            );

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowsAffected;
    }

    @Override
    public int addPlayerToTournament(int userId, int tournamentId) {
        String sql = "INSERT INTO tournament_players (user_id, tournament_id) VALUES (?, ?)";
        int rowsAffected;

        try {
            rowsAffected = jdbcTemplate.update(sql, userId, tournamentId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return rowsAffected;
    }

    public int getParticipantCount(int tournamentId) {
        String sql = "SELECT COUNT(*) FROM tournament_players " +
                "WHERE tournament_id = ?;";
        int count;

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, tournamentId);
            count = (result.next()) ? result.getInt("count") : 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return count;
    }

    @Override
    public List<UserDetails> getParticipants(int tournamentId) {
        List<UserDetails> participants = new ArrayList<>();
        String sql = "SELECT detail_id, ud.user_id, display_name, elo_rating, is_staff FROM tournament_players tp " +
                "JOIN users u ON tp.user_id = u.user_id " +
                "JOIN user_details ud ON u.user_id = ud.user_id " +
                "WHERE tournament_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tournamentId);
            while (results.next()) {
                participants.add(JdbcUserDetailsDao.mapRowToUserDetails(results));
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return participants;
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
        tournament.setMaxParticipants(result.getInt("max_participants"));
        tournament.setStartDate(result.getDate("start_date").toLocalDate());
        tournament.setEndDate(result.getDate("end_date") != null ? result.getDate("end_date").toLocalDate() : null);

        return tournament;
    }
}
