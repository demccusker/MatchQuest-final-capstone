package com.techelevator.model;

public class Tournament {
    private int tournamentId;
    private int bracketId;
    private int gameId;
    private int creatorId;
    private String name;
    private boolean isScrim;
    public Tournament() {

    }

    public Tournament(int tournamentId, int bracketId, int gameId, int creatorId, String name, boolean isScrim) {
        this.tournamentId = tournamentId;
        this.bracketId = bracketId;
        this.gameId = gameId;
        this.creatorId = creatorId;
        this.name = name;
        this.isScrim = isScrim;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getBracketId() {
        return bracketId;
    }

    public void setBracketId(int bracketId) {
        this.bracketId = bracketId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsScrim() {
        return isScrim;
    }

    public void setIsScrim(boolean isScrim) {
        this.isScrim = isScrim;
    }
}
