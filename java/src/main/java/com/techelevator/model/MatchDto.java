package com.techelevator.model;

public class MatchDto {
    private Boolean isScrim;
    private Integer player1Id;
    private Integer player2Id;
    private int player1Score;
    private int player2Score;
    private int winnerId;
    private Boolean isDraw;
    private String matchStartTime;
    private int matchId;

    public MatchDto() {}
    public MatchDto(Boolean isScrim, Integer player1Id, Integer player2Id, int player1Score, int player2Score, int winnerId, Boolean isDraw, String matchStartTime, int matchId) {
        this.isScrim = isScrim;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.winnerId = winnerId;
        this.isDraw = isDraw;
        this.matchStartTime = matchStartTime;
        this.matchId = matchId;
    }

    public Boolean isScrim() {
        return isScrim;
    }

    public void setIsScrim(Boolean isScrim) {
        this.isScrim = isScrim;
    }

    public Integer getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(Integer player1Id) {
        this.player1Id = player1Id;
    }

    public Integer getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(Integer player2Id) {
        this.player2Id = player2Id;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public Boolean isDraw() {
        return isDraw;
    }

    public void setIsDraw(Boolean isDraw) {
        this.isDraw = isDraw;
    }

    public String getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(String matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
}
