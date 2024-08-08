package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcGameDao implements GameDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcGameDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Game> getAllGames(){
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Game game = mapRowToGame(results);
                games.add(game);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return games;
    }
    @Override
    public Game getGameById(int gameId) {
        String sql = "SELECT * FROM game WHERE game_id = ?";
        Game game = null;

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, gameId);
            if (result.next()) {
                game = mapRowToGame(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (Exception e) {
            throw new DaoException("Error retrieving game with ID " + gameId, e);
        }

        return game;
    }
    @Override
    public  Game getGameByName(String name){
        String sql = "SELECT * FROM game WHERE name = ?";
        Game game = null;

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);
            if (result.next()) {
                game = mapRowToGame(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (Exception e) {
            throw new DaoException("Error retrieving game with name " + name, e);
        }

        return game;
    }


    private Game mapRowToGame(SqlRowSet result){
        Game game = new Game();
        game.setGameId(result.getInt("game_id"));
        game.setName(result.getString("name"));
        game.setDescription(result.getString("description"));
        game.setWinType(result.getInt("win_type"));
        game.setMaxPlayers(result.getInt("max_players"));
        game.setMinPlayers(result.getInt("min_players"));
        return game;
    }

}
// THIS IS FOR LATER IF WE WANT TO GO BACK AND DO THIS- STRETCH GOAL
//    @Override
//    public Game createGame(Game game){
//        Game newGame;
//
//        return null;
//    }
//    @Override
//    public Game updateGame(Game game){
//        return null;
//    }
//    @Override
//    public Game deleteGame(int gameId){
//        return null;
//    }
