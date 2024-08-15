package com.techelevator.dao;

import com.techelevator.model.Bracket;

import java.util.List;

public interface BracketDao {
    List<Bracket> createBracketTree(int numberOfMatches);

    List<Bracket> getBracketsFromRoot(int bracketId);
    List<Bracket> getBracketsIdOrder(int bracketId);
    List<Bracket> getAncestors(int bracketId);
    List<Bracket> getChildBrackets(int bracketId);

    Bracket getBracketByMatchId(int matchId);
    void updateBrackets(List<Bracket> brackets);
    boolean deleteBracketTree(int rootId);
}
