package com.techelevator.model;

public class Match {

    private int matchId;
    private int gameId;
    private boolean isScrim;
    private int player1Id;
    private int player2Id;
    private Double player1Score;
    private Double player2Score;
    private int winnerId;
    private Boolean isDraw;

    public Match(){}
    public Match(int matchId, int gameId, boolean isScrim, int player1Id, int player2Id, Double player1Score, Double player2Score, int winnerId, Boolean isDraw) {
        this.matchId = matchId;
        this.gameId = gameId;
        this.isScrim = isScrim;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.winnerId = winnerId;
        this.isDraw = isDraw;
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

    public void setScrim(boolean scrim) {
        isScrim = scrim;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(int player1Id) {
        this.player1Id = player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(int player2Id) {
        this.player2Id = player2Id;
    }

    public Double getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(Double player1Score) {
        this.player1Score = player1Score;
    }

    public Double getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(Double player2Score) {
        this.player2Score = player2Score;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public Boolean getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(Boolean isDraw) {
        this.isDraw = isDraw;
    }
}

