package com.techelevator.dao;

import com.techelevator.model.AddressFilter;
import com.techelevator.model.Tournament;

import java.util.List;

public interface TournamentDao {
    List<Tournament> getAllTournaments();
    List<Tournament> getActiveTournaments();
    List<Tournament> getPastTournaments();
    List<Tournament> getTournamentByLocation(AddressFilter addressFilter);
    List<Tournament> getTournamentsByCreatorId(int creatorId);
    Tournament createTournament(Tournament tournament);
    Tournament getTournamentById(int tournamentId);
    int updateTournament(Tournament tournament);
}
