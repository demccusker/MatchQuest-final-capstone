package com.techelevator.model;

public class MatchDto {



    private int player1Score;
    private int player2Score;

    private int winnerId;
    private Boolean isDraw;

    private String matchStartTime;



    private int matchId;

    public MatchDto (int player1Score, int player2Score, int winnerId, Boolean isDraw, String matchStartTime, int matchId){
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.winnerId = winnerId;
        this.isDraw = isDraw;
        this.matchStartTime = matchStartTime;
        this.matchId = matchId;
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

    public Boolean getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(Boolean draw) {
        isDraw = draw;
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
