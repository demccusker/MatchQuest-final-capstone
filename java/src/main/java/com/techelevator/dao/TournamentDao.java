package com.techelevator.dao;

import com.techelevator.model.Tournament;

import java.util.List;

public interface TournamentDao {
    List<Tournament> getAllTournaments();

    Tournament getTournamentById(int tournamentId);

    Tournament createTournament();

    Tournament updateTournament(Tournament tournament);


}
