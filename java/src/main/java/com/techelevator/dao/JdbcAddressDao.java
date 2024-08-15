package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Address;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcAddressDao implements AddressDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAddressDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Address addAddress(Address address) {
        Address newAddress;
        String sql = "INSERT INTO address(tournament_id, city, province, country) VALUES " +
                     "(?, ?, ?, ?) RETURNING tournament_id";

        try {
            int tournamentId = jdbcTemplate.queryForObject(sql, int.class,
                    address.getTournamentId(),
                    address.getCity(),
                    address.getProvince(),
                    address.getCountry()
            );

            newAddress = getAddress(tournamentId);
            if (!newAddress.getTournamentId().equals(address.getTournamentId())) throw new DaoException("Failed to add address for Tournament: " + address.getTournamentId());

        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database",e);
        } catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation",e);
        }

        return newAddress;
    }

    @Override
    public Address getAddress(int tournamentId) {
        Address address;
        String sql = "SELECT tournament_id, city, province, country FROM address " +
                     "WHERE tournament_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, tournamentId);
            address = (result.next()) ? mapRowSetToAddress(result) : null;

        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database",e);
        }

        return address;
    }

    @Override
    public boolean deleteAddress(int tournamentId) {
        String sql = "DELETE FROM address " +
                     "WHERE tournament_id = ?;";

        try {
            int rowsAffected = jdbcTemplate.update(sql, tournamentId);
            return rowsAffected == 1;

        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data integrity violation", ex);
        }
    }

    private Address mapRowSetToAddress(SqlRowSet results) {
        Address address = new Address();

        address.setTournamentId(results.getInt("tournament_id"));
        address.setCity(results.getString("city"));
        address.setProvince(results.getString("province"));
        address.setCountry(results.getString("country"));

        return address;
    }
}
