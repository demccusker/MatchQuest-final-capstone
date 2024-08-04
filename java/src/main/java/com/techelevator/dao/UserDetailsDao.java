package com.techelevator.dao;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsDao {

    List <UserDetails> getUsersDetails();

    UserDetails createUserDetails();

    UserDetails updateUserDetails();

    UserDetails getUserDetailsByUsername();

}
