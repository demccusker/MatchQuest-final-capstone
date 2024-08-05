package com.techelevator.controller;

import com.techelevator.dao.JdbcTournamentDao;
import com.techelevator.dao.TournamentDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Tournament;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
@PreAuthorize("isAuthenticated()")
@CrossOrigin

public class TournamentController {
    private final TournamentDao tournamentDao;


    public TournamentController(JdbcTemplate jdbcTemplate) {
        tournamentDao = new JdbcTournamentDao(jdbcTemplate);
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("permitAll")
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
}
