package com.techelevator.dao;

import com.techelevator.model.Bracket;

import java.util.List;

public interface BracketDao {

    List<Bracket> getBrackets();

    Bracket getBracketById (int bracketId);

    Bracket getBracketByName (String name);


}
