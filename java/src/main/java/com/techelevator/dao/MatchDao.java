package com.techelevator.dao;

import com.techelevator.model.Match;

import java.util.List;

public interface MatchDao {

    List<Match> getMatches();
    List<Match> getMatchesByPlayerId(int playerId);
    List<Match> getMatchesByTournamentId(int tournamentId);

    Match getMatchById(int matchId);
    Match createMatch(Match match);
}
