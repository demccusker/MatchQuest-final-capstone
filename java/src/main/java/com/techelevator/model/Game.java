package com.techelevator.model;

public class Game {
    private int gameId;
    private String name;
    private String description;
    private int winType;
    private int maxPlayers;
    private int minPlayers;
    public Game() { }

    public Game(int gameId, String name, String description,
                int winType, int maxPlayers, int minPlayers) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.winType = winType;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWinType(int winType) {
        this.winType = winType;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getWinType() {
        return winType;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }
}
