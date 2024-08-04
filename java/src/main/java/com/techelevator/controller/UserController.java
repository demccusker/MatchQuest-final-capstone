package com.techelevator.controller;

import com.techelevator.dao.JbdcUserDetailsDao;
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

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class UserController {
    private final UserDao userDao;
    private final UserDetailsDao detailsDao;

    public UserController(JdbcTemplate jdbcTemplate) {
        userDao = new JdbcUserDao(jdbcTemplate);
        detailsDao = new JbdcUserDetailsDao(jdbcTemplate);
    }

    @RequestMapping(path = "/{userId}/details", method = RequestMethod.GET)
    public UserDetailsDto getUserDetails(@PathVariable int userId) {
        try {
            UserDetails details = detailsDao.getUserDetailsById(userId);
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
}
