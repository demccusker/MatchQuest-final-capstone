package com.techelevator.dao;

import com.techelevator.model.UserDetails;
import com.techelevator.model.UserDetailsDto;

import java.util.List;

public interface UserDetailsDao {

    List<UserDetails> getUsersDetails();

    UserDetails createUserDetails(int userId, UserDetailsDto userDetailsDto);

    int updateUserDetails(UserDetails userDetails);

    UserDetails getUserDetailsByUsername(String username);

    UserDetails getUserDetailsByDetailId(int Id);

    UserDetails getUserDetailsByUserId(int id);

}
