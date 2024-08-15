package com.techelevator.controller;

import com.techelevator.dao.BracketDao;
import com.techelevator.dao.JdbcBracketDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Bracket;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/bracket")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class BracketController {
    private final BracketDao bracketDao;

    public BracketController(JdbcTemplate jdbcTemplate) {
        bracketDao = new JdbcBracketDao(jdbcTemplate);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/{bracketId}", method = RequestMethod.GET)
    public List<Bracket> getBracketTree(@PathVariable int bracketId, @RequestParam(required = false) String get) {
        return getBrackets(bracketId, get);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "match/{matchId}", method = RequestMethod.GET)
    public List<Bracket> getBracket(@PathVariable int matchId, @RequestParam(required = false) String get) {
        Bracket bracket;
        try {
            bracket = bracketDao.getBracketByMatchId(matchId);
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage()
            );
        }

        return getBrackets(bracket.getBracketId(), get);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public LinkedHashMap<String, Object> updateBrackets(@RequestBody List<Bracket> brackets) {
        try {
            bracketDao.updateBrackets(brackets);
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage()
            );
        }

        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        List<Bracket> ancestors = bracketDao.getAncestors(brackets.get(0).getBracketId());

        if (!ancestors.isEmpty()) {
            response.put("root",
                    ancestors.get(0).getBracketId()
            );
        } else {
            response.put("root",
                    brackets.get(0).getBracketId()
            );
        }

        return response;
    }

    private List<Bracket> getBrackets(int bracketId, String get) {
        get = (get == null) ? "tree" : get;

        try {
            switch(get) {
                case "tree":
                    return bracketDao.getBracketsFromRoot(bracketId);
                case "idOrderList":
                    return bracketDao.getBracketsIdOrder(bracketId);
                case "ancestors":
                    return bracketDao.getAncestors(bracketId);
                case "children":
                    return bracketDao.getChildBrackets(bracketId);
                default:
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            get + " is not a valid value for Request param 'get'."
                    );
            }
        } catch (DaoException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage()
            );
        }
    }
}

