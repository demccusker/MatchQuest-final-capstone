package com.techelevator.dao;

import com.techelevator.model.Match;
import com.techelevator.model.MatchDto;

import javax.validation.Valid;
import java.util.List;

public interface MatchDao {

    List<Match> getMatches();
    List<Match> getMatchesByPlayerId(int playerId);
    List<Match> getMatchesByTournamentId(int tournamentId);

    Match getMatchById(int matchId);
    Match createMatch(Match match);

    int updateMatch(@Valid MatchDto match);
}
