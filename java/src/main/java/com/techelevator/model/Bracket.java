package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bracket {
    private int bracketId;
    @JsonProperty("parentBracket")
    private int parentId;
    @JsonIgnore
    private Bracket parentBracket = null;
    private int matchId;
    private String name;

    public Bracket() {}
    public Bracket(int bracketId, int parentId, Bracket parentBracket, int matchId, String name) {
        this.bracketId = bracketId;
        this.parentId = parentId;
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

    public int getParentId() { return parentId; }

    public void setParentId(int parentId) { this.parentId = parentId; }

    public Bracket getParentBracket() {
        return parentBracket;
    }

    public void setParentBracket(Bracket parentBracket) {
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
