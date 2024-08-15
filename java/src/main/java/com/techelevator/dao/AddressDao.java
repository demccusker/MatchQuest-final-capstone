package com.techelevator.dao;

import com.techelevator.model.Address;

public interface AddressDao {
    Address addAddress(Address address);
    Address getAddress(int tournamentId);

    boolean deleteAddress(int tournamentId);
}