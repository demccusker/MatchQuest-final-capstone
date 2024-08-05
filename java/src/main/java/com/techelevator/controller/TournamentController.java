package com.techelevator.controller;

import com.techelevator.dao.TournamentDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/tournaments")
@PreAuthorize("isAuthenticated()")
@CrossOrigin

public class TournamentController {
    private final TournamentDao tournamentDao;



}
