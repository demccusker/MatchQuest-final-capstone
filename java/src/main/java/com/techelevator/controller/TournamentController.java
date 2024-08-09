package com.techelevator.controller;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;
import com.techelevator.dao.*;
import com.techelevator.exception.DaoException;
import com.techelevator.model.AddressFilter;
import com.techelevator.model.Tournament;
import com.techelevator.model.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
@PreAuthorize("isAuthenticated()")
@CrossOrigin

public class TournamentController {
    private final TournamentDao tournamentDao;
    private final UserDetailsDao detailsDao;

    public TournamentController(JdbcTemplate jdbcTemplate) {
        tournamentDao = new JdbcTournamentDao(jdbcTemplate);
        detailsDao = new JdbcUserDetailsDao(jdbcTemplate);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Tournament createTournament(@RequestBody Tournament tournament, Principal caller) {
        Tournament newTournament;
        String name = caller.getName();
        UserDetails userDetails = detailsDao.getUserDetailsByUsername(caller.getName());
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
    @RequestMapping( method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        try {
            tournaments = tournamentDao.getAllTournaments();
            if (tournaments.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Unable to locate tournaments");
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }
        return tournaments;
    }

    @RequestMapping(path = "/{tournamentId}", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public Tournament getTournamentById(@PathVariable int tournamentId) {
        Tournament tournament = new Tournament();
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
    @RequestMapping(path="/organizer/{creatorId}")
    public List<Tournament> getTournamentsByCreatorID(@PathVariable int creatorId){
        List <Tournament>  tournamentsFromCreator;
        try{
            tournamentsFromCreator = tournamentDao.getTournamentsByCreatorId(creatorId);
            if(tournamentsFromCreator.isEmpty() ){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tournaments are found from creator ID:"+creatorId);
            }
        }catch (DaoException ex){
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, ex.getMessage());
        }
        return tournamentsFromCreator;
    }


    @RequestMapping(path = "/active", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public List<Tournament> getActiveTournaments() {
        List<Tournament> tournaments;
        try {
            tournaments = tournamentDao.getActiveTournaments();
            if (tournaments.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No active tournaments found");
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, ex.getMessage());
        }
        return tournaments;
    }
    @RequestMapping(path = "/past", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public List<Tournament> getPastTournaments() {
        List<Tournament> tournaments;
        try {
            tournaments = tournamentDao.getPastTournaments();
            if (tournaments.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No past tournaments found");
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, ex.getMessage());
        }
        return tournaments;
    }
    @RequestMapping(path = "/filterByLocation", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public List<Tournament> getTournamentsByLocation(AddressFilter addressFilter) {
        List<Tournament> tournaments;
        try {
            tournaments = tournamentDao.getTournamentByLocation(addressFilter);
            if (tournaments.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tournaments found for the specified location filters");
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, ex.getMessage());
        }
        return tournaments;
    }

    @RequestMapping(path = "/{tournamentId}/update", method = RequestMethod.PUT)
    @PreAuthorize("permitAll")
    public Tournament updateTournament(@RequestBody @Valid Tournament tournament, @PathVariable int tournamentId) {
        tournament.setTournamentId(tournamentId);

        Tournament updatedTournament = new Tournament();
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


}
