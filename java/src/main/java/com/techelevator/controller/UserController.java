package com.techelevator.controller;

import com.techelevator.dao.JdbcUserDetailsDao;
import com.techelevator.dao.JdbcUserDao;
import com.techelevator.dao.UserDao;
import com.techelevator.dao.UserDetailsDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.UserDetails;
import com.techelevator.model.UserDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class UserController {
    private final UserDao userDao;
    private final UserDetailsDao detailsDao;

    public UserController(JdbcTemplate jdbcTemplate) {
        userDao = new JdbcUserDao(jdbcTemplate);
        detailsDao = new JdbcUserDetailsDao(jdbcTemplate);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{userId}/details", method = RequestMethod.GET)
    public UserDetailsDto getUserDetails(@PathVariable int userId) {
        try {
            UserDetails details = detailsDao.getUserDetailsByUserId(userId);
            if (details == null) throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Unable to locate user details with provided ID: " + userId
            );

            return UserDetailsDto.convertToDto(details);

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetailsDto addUserDetails(@Valid @RequestBody UserDetailsDto detailsBody, @PathVariable int userId) {
        UserDetails details;

        try {
            details = detailsDao.getUserDetailsByUserId(userId);
            if (details != null) throw new ResponseStatusException(
                    HttpStatus.ALREADY_REPORTED, "User already has details"
            );

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }

        try {
            details = detailsDao.createUserDetails(userId, detailsBody);
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage()
            );
        }

        return UserDetailsDto.convertToDto(details);
    }

    @RequestMapping(path= "/{userId}/details", method = RequestMethod.PUT)
    public UserDetails updateDetails(@RequestBody UserDetails userDetails, @PathVariable int userId ) {
        userDetails.setUserId(userId);
        try {
            UserDetails updatedUserDetails;
            int rowsEffected = detailsDao.updateUserDetails(userDetails);
            System.out.println("number of rows affected in the controller: " + rowsEffected);
            if (rowsEffected == 0) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "unable to update user detail" + userId);
            }
            updatedUserDetails = detailsDao.getUserDetailsByUserId(userId);
            if(updatedUserDetails == null){
                throw new ResponseStatusException (
                        HttpStatus.REQUEST_TIMEOUT, "user id is not valid "
                );

            }
            System.out.println("userDeatils display name after controller: " + updatedUserDetails.getDisplayName());
            return updatedUserDetails;
        }catch (DaoException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }

    }


}
