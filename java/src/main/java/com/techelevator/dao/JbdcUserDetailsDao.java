package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;


public class JbdcUserDetailsDao implements UserDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public JbdcUserDetailsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserDetails> getUsersDetails() {
        List<UserDetails> usersDetails = new ArrayList<>();
        String SQL = "SELECT * FROM user_details";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(SQL);
            while (results.next()) {
                UserDetails userDetails = mapRowToUserDetails
            }

        }
        catch {
    }
    }

    UserDetails createUserDetails();

    UserDetails updateUserDetails();

    UserDetails getUserDetailsByUsername();

private UserDetails mapRowToUserDetails(SqlRowSet results)   {
    UserDetails userDetails = new UserDetails();
    userDetails.setDetailId(results.getInt("details_id));
    userDetails.setUserId(results.getInt("user_id"));
    userDetails.setDisplayName(results.getString("display_name"));
    userDetails.setEloRating(results.getInt("elo_rating"));
    userDetails.setIsStaff(results.getBoolean("is_staff"));
}




}
