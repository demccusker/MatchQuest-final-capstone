package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tournaments")
@PreAuthorize("isAuthenticated()")
@CrossOrigin

public class TournamentController {
    private final TournamentDao tournamentDao;
    private final BracketDao bracketDao;
    private final UserDetailsDao detailsDao;

    public TournamentController(JdbcTemplate jdbcTemplate) {
        tournamentDao = new JdbcTournamentDao(jdbcTemplate);
        bracketDao = new JdbcBracketDao(jdbcTemplate);
        detailsDao = new JdbcUserDetailsDao(jdbcTemplate);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Tournament createTournament(@RequestBody Tournament tournament, Principal caller) {
        Tournament newTournament;
        String name = caller.getName();
        UserDetails userDetails = detailsDao.getUserDetailsByUsername(name);
        if (!userDetails.getIsStaff()) throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "You are not allowed to access this content."
        );

        try {
            newTournament = tournamentDao.createTournament(tournament);
            if (newTournament == null) throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Unable to locate tournaments"
            );

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }

        return newTournament;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{tournamentId}", method = RequestMethod.GET)
    public Tournament getTournamentById(@PathVariable int tournamentId) {
        Tournament tournament;

        try {
            tournament = tournamentDao.getTournamentById(tournamentId);
            if (tournament == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Unable to locate tournament by tournament ID: " + tournamentId);
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }

        return tournament;
    }

    @PreAuthorize("permitAll")
    @RequestMapping( method = RequestMethod.GET)
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments;
        try {
            tournaments = tournamentDao.getAllTournaments();
            if (tournaments.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Unable to locate tournaments"
                );
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }
        return tournaments;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/query", method = RequestMethod.POST)
    public List<Tournament> queryTournaments(@RequestBody Map<String, Object> query) {
        List<Tournament> tournaments;
        QueryFilter filter = new QueryFilter(query);

        try {
            tournaments = tournamentDao.getTournamentsByFilter(filter);
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }

        return tournaments;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{tournamentId}/update", method = RequestMethod.PUT)
    public Tournament updateTournament(@RequestBody @Valid Tournament tournament, @PathVariable int tournamentId) {
        tournament.setTournamentId(tournamentId);

        Tournament updatedTournament;
        try {

            int rowsAffected = tournamentDao.updateTournament(tournament);

            if (rowsAffected == 0) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unable to update tournament " + tournamentId);
            }
            updatedTournament = tournamentDao.getTournamentById(tournamentId);

            if(updatedTournament == null){
                throw new ResponseStatusException (
                        HttpStatus.REQUEST_TIMEOUT, "Tournament ID is not valid "
                );

            }

        }catch (DaoException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
        return updatedTournament;

    }

    @RequestMapping(path = "/{tournamentId}/join", method = RequestMethod.POST)
    public int joinTournament(@PathVariable int tournamentId, Principal principal) {
        String name = principal.getName();
        UserDetails userDetails = detailsDao.getUserDetailsByUsername(name);
        int userId = userDetails.getUserId();
        System.out.println("user id; " + userId);
        System.out.println("tournament id: " + tournamentId);
        int rowsAffected = 0;
        try {
            rowsAffected = tournamentDao.addPlayerToTournament(userId,tournamentId);
            if (rowsAffected == 0) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unable to add player to tournament");
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return rowsAffected;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/create-brackets/{count}", method = RequestMethod.GET)
    public List<Bracket> createBracketTree(@PathVariable int count) {
        return bracketDao.createBracketTree(count);
    }


    @PreAuthorize("permitAll")
    @RequestMapping(path = "/brackets/{bracketId}/tree", method = RequestMethod.GET)
    public List<Bracket> getBracketTree(@PathVariable int bracketId) {
        return bracketDao.getBracketsFromRoot(bracketId);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/brackets/{bracketId}/ancestors", method = RequestMethod.GET)
    public List<Bracket> getAncestorBrackets(@PathVariable int bracketId) {
        return bracketDao.getAncestors(bracketId);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/brackets/{bracketId}/children", method = RequestMethod.GET)
    public List<Bracket> getChildrenBrackets(@PathVariable int bracketId) {
        return bracketDao.getChildBrackets(bracketId);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/brackets/{bracketId}", method = RequestMethod.DELETE)
    public void deleteBracketTree(@PathVariable int bracketId) {
        if (!bracketDao.deleteBracketTree(bracketId))
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while deleting Bracket Tree"
            );
    }
}
