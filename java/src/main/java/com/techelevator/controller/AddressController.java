package com.techelevator.controller;

import com.techelevator.dao.AddressDao;
import com.techelevator.dao.JdbcAddressDao;
import com.techelevator.dao.JdbcTournamentDao;
import com.techelevator.dao.TournamentDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Address;
import com.techelevator.model.Tournament;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/address")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class AddressController {
    private final AddressDao addressDao;
    private final TournamentDao tournamentDao;

    public AddressController(JdbcTemplate jdbcTemplate) {
        addressDao = new JdbcAddressDao(jdbcTemplate);
        tournamentDao = new JdbcTournamentDao(jdbcTemplate);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Address addAddress(@RequestBody Address address) {
        Address newAddress;

        try {
            Tournament tournament = tournamentDao.getTournamentById(address.getTournamentId());
            if (tournament.isOnline()) throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE, "Cannot assign address to an online tournament!"
            );

            newAddress = addressDao.addAddress(address);
            assignStreetNumber(tournamentDao, address.getTournamentId(), newAddress);

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage()
            );
        }

        return newAddress;
    }

    @RequestMapping(path = "/{tournamentId}", method = RequestMethod.GET)
    public Address getAddress(@PathVariable int tournamentId) {
        Address address;

        try {
            address = addressDao.getAddress(tournamentId);
            if (address == null) throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Address not found for Tournament: " + tournamentId
            );
            assignStreetNumber(tournamentDao, tournamentId, address);

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }

        return address;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateAddress(@RequestBody Address address) {
        try {
            if (!addressDao.updateAddress(address)) throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update address!"
            );
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }
    }

    @RequestMapping(path = "/{tournamentId}", method = RequestMethod.DELETE)
    public void deleteAddress(@PathVariable int tournamentId) {
        try {
            if (!addressDao.deleteAddress(tournamentId)) throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Unable to delete address for Tournament: " + tournamentId
            );
            Tournament tournament = tournamentDao.getTournamentById(tournamentId);
            tournament.setLocation(null);

            tournamentDao.updateTournament(tournament);

        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage()
            );
        }
    }

    private static void assignStreetNumber(TournamentDao tournamentDao, Integer tournamentId, Address newAddress) {
        Tournament tournament = tournamentDao.getTournamentById(tournamentId);
        newAddress.setStreetNumber(tournament.getLocation());
    }
}
