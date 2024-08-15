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
import java.util.*;

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
        UserDetails userDetails = getUserDetailsByCaller(detailsDao, caller);

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
    public int joinTournament(@PathVariable int tournamentId, Principal caller) {
        int userId = getUserIdByCaller(detailsDao, caller);
        int rowsAffected;

        try {
            rowsAffected = tournamentDao.addPlayerToTournament(userId, tournamentId);
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
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/{tournamentId}/brackets", method = RequestMethod.POST)
    public List<Bracket> createBracketTree(@PathVariable int tournamentId) {
        List<Bracket> tree;
        HashMap<Integer, Bracket> nodeMap = new HashMap<>();

        try {
            Tournament tournament;
            List<UserDetails> participants;
            int participantCount;

            tournament = tournamentDao.getTournamentById(tournamentId);
            if (tournament.getBracketId() != 0) throw new ResponseStatusException(
                    HttpStatus.ALREADY_REPORTED,
                    "Bracket has already been created for this tournament. Bracket regeneration currently not supported!"
            );

            participants = tournamentDao.getParticipants(tournamentId);
            participantCount = tournamentDao.getParticipantCount(tournamentId);

            if (participants.size() != participantCount) throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Number of participants does not match participant count in cloud!"
            );
            else if (participantCount < 2) throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Tournament must have more than one person signed up to create bracket! Currently has: " + participantCount + " signed up participants."
            );

            int requiredMatches = (participantCount < 3) ? 1 : participantCount - 1;
            tree = bracketDao.createBracketTree(requiredMatches);

            tournament.setBracketId(tree.get(0).getBracketId());
            tournamentDao.updateTournament(tournament);

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage()
            );
        }

        List<Bracket> availableNodes = new ArrayList<>();
        for (Bracket node : tree) {
            List<Bracket> children = bracketDao.getChildBrackets(node.getBracketId());
            if (children.size() < 2) availableNodes.add(node);
        }

        return availableNodes;
    }


    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{tournamentId}/brackets", method = RequestMethod.GET)
    public List<Bracket> getBracketTree(@PathVariable int tournamentId) {
        try {
            Tournament tournament = tournamentDao.getTournamentById(tournamentId);
            int bracketId = tournament.getBracketId();

            if (bracketId == 0) throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot retrieve bracket information from a tournament that does not contain a bracket. Please generate a bracket for this tournament!"
            );

            return bracketDao.getBracketsIdOrder(bracketId);

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage()
            );
        }
    }

    @RequestMapping(path="/{tournamentId}/participants",method = RequestMethod.GET)
    public List<UserDetails> getParticipants(@PathVariable int tournamentId){
        List<UserDetails> userDetailsList = null;
        try{
            userDetailsList = tournamentDao.getParticipants(tournamentId);
        }
        catch(DaoException ex){
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT,
                    ex.getMessage()
            );
        }
        return userDetailsList;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{tournamentId}/brackets", method = RequestMethod.DELETE)
    public void deleteBracketTree(@PathVariable int tournamentId) {
        try {
            Tournament tournament = tournamentDao.getTournamentById(tournamentId);
            int bracketId = tournament.getBracketId();

            tournament.setBracketId(0);
            tournamentDao.updateTournament(tournament);

            if (bracketId == 0) throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT,
                    "Cannot retrieve bracket information from a tournament that does not contain a bracket. Please generate a bracket for this tournament!"
            );

            if (!bracketDao.deleteBracketTree(bracketId)) {
                /* Restore ID if delete was unsuccessful */
                tournament.setBracketId(bracketId);
                tournamentDao.updateTournament(tournament);

                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error while deleting Bracket Tree"
                );
            }

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage()
            );
        }
    }

    private static int getUserIdByCaller(UserDetailsDao details, Principal caller) {
        try {
            return details.getUserDetailsByUsername(caller.getName()).getUserId();
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT,
                    ex.getMessage()
            );
        }
    }

    private static UserDetails getUserDetailsByCaller(UserDetailsDao details, Principal caller) {
        try {
            return details.getUserDetailsByUsername(caller.getName());
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT,
                    ex.getMessage()
            );
        }
    }
}
