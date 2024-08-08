package com.techelevator.dao;


import com.techelevator.model.Tournament;
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


//public class JbdcTournamentTest extends BaseDaoTests {

//    private JdbcTournamentDao sut;
//
//
//    Tournament tournament = new Tournament(int tournamentId, int bracketId, int gameId, int creatorId, String name, boolean isScrim)
//
//    @Before
//    public void setup() {
//        sut = new JdbcTournamentDao(new JdbcTemplate());
//    }
//
//    @Test
//    public void getAllTournaments_returns_all_tournaments() {
//        List<Tournament> tournaments = sut.getAllTournaments();
//        Assert.assertNotNull(tournaments);
//        Assert.assertFalse(tournaments.isEmpty());
//    }
//
//    @Test
//    public void getTournamentById_returns_correct_tournament() {
//        // Arrange
//        Tournament expectedTournament = new Tournament();
//        expectedTournament.setName("Test Tournament");
//        expectedTournament.setGameId(1);
//        expectedTournament.setBracketId(1);
//        expectedTournament.setCreatorId(1);
//        expectedTournament.setIsScrim(false);
//
//        Tournament createdTournament = sut.createTournament(expectedTournament);
//        int createdTournamentId = createdTournament.getId();
//
//        // Act
//        Tournament actualTournament = sut.getTournamentById(createdTournamentId);
//
//        // Assert
//        Assert.assertNotNull(actualTournament);
//        Assert.assertEquals(expectedTournament.getName(), actualTournament.getName());
//        Assert.assertEquals(expectedTournament.getGameId(), actualTournament.getGameId());
//        Assert.assertEquals(expectedTournament.getBracketId(), actualTournament.getBracketId());
//    }
//
//    @Test
//    public void createTournament_creates_new_tournament() {
//        // Arrange
//        Tournament newTournament = new Tournament();
//        newTournament.setName("New Tournament");
//        newTournament.setGameId(1);
//        newTournament.setBracketId(1);
//        newTournament.setCreatorId(1);
//        newTournament.setIsScrim(false);
//
//        // Act
//        Tournament createdTournament = sut.createTournament(newTournament);
//
//        // Assert
//        Assert.assertNotNull(createdTournament);
//        Assert.assertTrue(createdTournament.getId() > 0);
//        Assert.assertEquals(newTournament.getName(), createdTournament.getName());
//    }
//
//    @Test
//    public void updateTournament_updates_existing_tournament() {
//        // Arrange
//        Tournament tournamentToUpdate = new Tournament();
//        tournamentToUpdate.setName("Update Tournament");
//        tournamentToUpdate.setGameId(1);
//        tournamentToUpdate.setBracketId(1);
//        tournamentToUpdate.setCreatorId(1);
//        tournamentToUpdate.setIsScrim(false);
//
//        Tournament createdTournament = sut.createTournament(tournamentToUpdate);
//        createdTournament.setName("Updated Tournament");
//
//        // Act
//        Tournament updatedTournament = sut.updateTournament(createdTournament);
//
//        // Assert
//        Assert.assertNotNull(updatedTournament);
//        Assert.assertEquals("Updated Tournament", updatedTournament.getName());
//    }
//
//    @Test
//    public void deleteTournament_removes_tournament() {
//        // Arrange
//        Tournament tournamentToDelete = new Tournament();
//        tournamentToDelete.setName("Delete Tournament");
//        tournamentToDelete.setGameId(1);
//        tournamentToDelete.setBracketId(1);
//        tournamentToDelete.setCreatorId(1);
//        tournamentToDelete.setIsScrim(false);
//
//        Tournament createdTournament = sut.createTournament(tournamentToDelete);
//        int createdTournamentId = createdTournament.getId();
//
//        // Act
//        sut.deleteTournament(createdTournamentId);
//
//        // Assert
//        Tournament deletedTournament = sut.getTournamentById(createdTournamentId);
//        Assert.assertNull(deletedTournament);
//    }
//}
//
