package com.techelevator.dao;

import com.techelevator.model.UserDetails;

import java.util.List;

public interface UserDetailsDao {

    List<UserDetails> getUsersDetails();

    UserDetails createUserDetails();

    UserDetails updateUserDetails();

    UserDetails getUserDetailsByUsername();

}
