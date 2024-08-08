package com.techelevator.dao;

import com.techelevator.model.Game;

import java.util.List;

public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int gameId);
    Game getGameByName(String name);

//    Game createGame(Game game);
//    Game updateGame(Game game);
//    Game deleteGame(int gameId);



}
