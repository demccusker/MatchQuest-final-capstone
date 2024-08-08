package com.techelevator.model;

public class Bracket {

    private int bracketId;

    private int parentBracket;

    private int matchId;

    private String name;

    public Bracket(int bracketId, int parentBracket, int matchId, String name) {
        this.bracketId = bracketId;
        this.parentBracket = parentBracket;
        this.matchId = matchId;
        this.name = name;
    }

    public int getBracketId() {
        return bracketId;
    }

    public void setBracketId(int bracketId) {
        this.bracketId = bracketId;
    }

    public int getParentBracket() {
        return parentBracket;
    }

    public void setParentBracket(int parentBracket) {
        this.parentBracket = parentBracket;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
