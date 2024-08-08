package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.dao.JdbcGameDao;
import com.techelevator.dao.JdbcUserDetailsDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.Tournament;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class GameController {
    private final GameDao gameDao;

    public GameController(JdbcTemplate jdbcTemplate){
        gameDao = new JdbcGameDao(jdbcTemplate);
    }
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/games" ,method = RequestMethod.GET)
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        try {
            games = gameDao.getAllGames();
            if (games.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Unable to locate games");
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }
        return games;
    }
    @RequestMapping(path = "/game/{gameId}", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public Game getGameById(@PathVariable int gameId) {
        Game game = new Game();
        try {
            game = gameDao.getGameById(gameId);
            if (game == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Unable to locate game by game ID: " + gameId);
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }

        return game;
    }
    @RequestMapping(path = "game/name/{name}", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public Game getGameByName(@PathVariable String name) {
        try {
            Game game = gameDao.getGameByName(name);
            if (game == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Game with name '" + name + "' not found");
            }
            return game;
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.REQUEST_TIMEOUT, ex.getMessage()
            );
        }
    }
}
