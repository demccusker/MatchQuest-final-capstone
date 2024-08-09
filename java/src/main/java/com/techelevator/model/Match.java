package com.techelevator.model;

public class Match {

    private int matchId;

    private int gameId;

    private boolean isScrim;

    public Match(int matchId, int gameId, int roundCount, boolean isScrim) {
        this.matchId = matchId;
        this.gameId = gameId;
        this.isScrim = isScrim;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public boolean getIsScrim() {
        return isScrim;
    }

    public void setIsScrim(boolean isScrim) {
        this.isScrim = isScrim;
    }
}

