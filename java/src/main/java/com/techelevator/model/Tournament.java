package com.techelevator.model;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Date;

public class Tournament {
    private int tournamentId;
    private int bracketId;
    private int gameId;
    private int creatorId;
    private String name;
    private boolean isScrim;
    private boolean isOnline;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxParticipants;

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public Tournament() {}
    public Tournament(int tournamentId, int bracketId, int gameId, int creatorId, String name,
                      boolean isScrim, boolean isOnline, String location,int maxParticipants,
                      LocalDate startDate, LocalDate endDate) {
        this.tournamentId = tournamentId;
        this.bracketId = bracketId;
        this.gameId = gameId;
        this.creatorId = creatorId;
        this.name = name;
        this.isScrim = isScrim;
        this.isOnline = isOnline;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipants = maxParticipants;

    }

    public void setIsOnline(boolean IsOnline) {
        isOnline = IsOnline;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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
