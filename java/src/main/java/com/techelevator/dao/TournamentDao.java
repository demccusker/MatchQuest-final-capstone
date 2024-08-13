package com.techelevator.dao;

import com.techelevator.model.AddressFilter;
import com.techelevator.model.QueryFilter;
import com.techelevator.model.Tournament;

import java.util.List;

public interface TournamentDao {
    List<Tournament> getAllTournaments();
    List<Tournament> getActiveTournaments();
    List<Tournament> getPastTournaments();
    List<Tournament> getTournamentsByCreatorId(int creatorId);
    List<Tournament> getTournamentsByLocation(AddressFilter addressFilter);
    List<Tournament> getTournamentsByFilter(QueryFilter filter);
    Tournament createTournament(Tournament tournament);
    Tournament getTournamentById(int tournamentId);
    int updateTournament(Tournament tournament);
    int addPlayerToTournament(int userId, int tournamentId);
}
