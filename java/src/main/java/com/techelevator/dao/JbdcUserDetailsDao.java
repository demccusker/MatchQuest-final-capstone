package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.UserDetailsDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.model.UserDetails;

import java.util.ArrayList;
import java.util.List;


public class JbdcUserDetailsDao implements UserDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public JbdcUserDetailsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserDetails> getUsersDetails() {
        List<UserDetails> details = new ArrayList<>();
        String SQL = "SELECT * FROM user_details";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(SQL);
            while (results.next()) {
                UserDetails userDetails = mapRowToUserDetails(results);
                details.add(userDetails);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return details;
    }

    @Override
    public UserDetails createUserDetails(int userId, UserDetailsDto userDetailsDto) {
     UserDetails userDetails = new UserDetails();
     String SQL = "INSERT INTO user_details (user_id, display_name, elo_rating, is_staff) " +
             "VALUES(?,?,?,?) RETURNING detail_id;";
        try {
            int detailId = jdbcTemplate.queryForObject(SQL,int.class,
                    userId,
                    userDetailsDto.getDisplayName(),
                    userDetailsDto.getEloRating(),
                    userDetailsDto.isStaff()
            );
            userDetails = getUserDetailsById(detailId);
            if(userDetails == null){
                throw new DaoException("User Detail is null");
            }
            return userDetails;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation",e);
        }

    }

    @Override
    public UserDetails updateUserDetails(){
        return null;
    };

    @Override
    public UserDetails getUserDetailsByUsername(String username){
        return null;
    };

    @Override
    public UserDetails getUserDetailsById(int detailId){
        UserDetails userDetails = new UserDetails();
        String SQL = "SELECT * FROM user_details WHERE detail_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(SQL,detailId);
            if (results.next()) {
                userDetails = mapRowToUserDetails(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return userDetails;
    }

private UserDetails mapRowToUserDetails(SqlRowSet results)   {
    UserDetails userDetails = new UserDetails();
    userDetails.setDetailId(results.getInt("details_id"));
    userDetails.setUserId(results.getInt("user_id"));
    userDetails.setDisplayName(results.getString("display_name"));
    userDetails.setEloRating(results.getInt("elo_rating"));
    userDetails.setStaff(results.getBoolean("is_staff"));

    return userDetails;
}




}
