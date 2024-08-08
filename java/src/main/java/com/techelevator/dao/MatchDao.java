package com.techelevator.dao;

import com.techelevator.model.Match;

import java.util.List;

public interface MatchDao {

    List<Match> getMatches();

    Match getMatchById (int matchId);


}
