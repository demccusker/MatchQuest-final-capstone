package com.techelevator.dao;

import com.techelevator.model.User;
import com.techelevator.model.UserDetails;
import com.techelevator.model.UserDetailsDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.exception.DaoException;

import java.util.List;

public class JdbcUserDetailsDaoTest extends BaseDaoTests {


        private JdbcUserDetailsDao sut;
        private JdbcUserDao userDao;

        private static final String USER_1 = "user1";
        private static final String USER_2 = "user2";
        private static final String USER_3 = "user3";

        private static final UserDetailsDto user1Details = new UserDetailsDto("User1", 800, false);
        private static final UserDetailsDto user2Details = new UserDetailsDto("User2", 1200, true);
        private static final UserDetailsDto user3Details = new UserDetailsDto("User3", 900, false);

        @Before
        public void setup() {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            sut = new JdbcUserDetailsDao(jdbcTemplate);
            userDao = new JdbcUserDao(jdbcTemplate);
        }

        @Test
        public void shouldReturnInputUserWhenCreatingUserDetails() {
            User user = userDao.getUserByUsername(USER_1);
            sut.createUserDetails(user.getId(), user1Details);

            UserDetailsDto actualDetails = UserDetailsDto.convertToDto(sut.getUserDetailsByUserId(user.getId()));
            assertDetailsEquals(user1Details, actualDetails);
        }

        @Test(expected = IllegalArgumentException.class)
        public void shouldThrowExceptionWhenCreatingUserDetailsWithNull() {
            sut.createUserDetails(userDao.getUserByUsername(USER_1).getId(), null);
        }

        @Test
        public void shouldGetUserDetailsByUsername() {
            User user = userDao.getUserByUsername(USER_1);
            UserDetails actualDetails = sut.getUserDetailsByUsername(user.getUsername());
            assertDetailsEquals(user1Details, UserDetailsDto.convertToDto(actualDetails));
        }

        @Test
        public void shouldUpdateUserDetails() {
            User user = userDao.getUserByUsername(USER_1);
            UserDetails userDetails = sut.getUserDetailsByUserId(user.getId());

            userDetails.setDisplayName("UpdatedUser1");
            userDetails.setEloRating(1000);
            userDetails.setIsStaff(true);

            int rowsAffected = sut.updateUserDetails(userDetails);
            Assert.assertEquals(1, rowsAffected);

            UserDetails updatedDetails = sut.getUserDetailsByUserId(user.getId());
            assertDetailsEquals(UserDetailsDto.convertToDto(userDetails), UserDetailsDto.convertToDto(updatedDetails));
        }

        @Test
        public void shouldGetUserDetailsByDetailId() {
            User user = userDao.getUserByUsername(USER_1);
            UserDetails createdDetails = sut.createUserDetails(user.getId(), user1Details);

            UserDetails retrievedDetails = sut.getUserDetailsByDetailId(createdDetails.getDetailId());
            assertDetailsEquals(UserDetailsDto.convertToDto(createdDetails), UserDetailsDto.convertToDto(retrievedDetails));
        }

        @Test
        public void shouldGetAllUsersDetails() {
            List<UserDetails> userDetailsList = sut.getUsersDetails();
            Assert.assertEquals(3, userDetailsList.size());

            assertDetailsEquals(user1Details, UserDetailsDto.convertToDto(userDetailsList.get(0)));
            assertDetailsEquals(user2Details, UserDetailsDto.convertToDto(userDetailsList.get(1)));
            assertDetailsEquals(user3Details, UserDetailsDto.convertToDto(userDetailsList.get(2)));
        }

        @Test
        public void shouldReturnZeroWhenUpdatingNonExistingUserDetails() {
            UserDetails nonExistingUserDetails = new UserDetails();
            nonExistingUserDetails.setDetailId(99999);
            nonExistingUserDetails.setDisplayName("NonExistingUser");
            nonExistingUserDetails.setEloRating(500);
            nonExistingUserDetails.setIsStaff(false);
            int rowsAffected = sut.updateUserDetails(nonExistingUserDetails);
            Assert.assertEquals(0, rowsAffected);
        }

        private void assertDetailsEquals(UserDetailsDto expected, UserDetailsDto actual) {
            Assert.assertEquals("Display names should match", expected.getDisplayName(), actual.getDisplayName());
            Assert.assertEquals("Elo ratings should match", expected.getEloRating(), actual.getEloRating());
            Assert.assertEquals("Staff status should match", expected.getIsStaff(), actual.getIsStaff());
        }
    }
