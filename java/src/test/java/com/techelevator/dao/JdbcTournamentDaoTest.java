package com.techelevator.dao;

import com.techelevator.model.Tournament;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;


public class JdbcTournamentDaoTest extends BaseDaoTests {

    private static LocalDate date1 = LocalDate.parse("2024-02-23");
    private static LocalDate date2 = LocalDate.parse("2024-08-07");

    private static LocalDate date3 = LocalDate.parse("2024-08-31");

    private static LocalDate date4 = LocalDate.parse("2024-04-23");

    private static LocalDate date5 = LocalDate.parse("2024-05-01");
    protected static final Tournament TOURNAMENT1 = new Tournament(1, 1, 2, 3, "Golf Tournament", false, false, "China", date1, null);
    protected static final Tournament TOURNAMENT2 = new Tournament (2, 1, 2, 3, "Golf Tournament", false, false, null, date2, date3);

    protected static final Tournament TOURNAMENT3 = new Tournament(4,5,3,5,"IDONTKNOW", true, true, "Online", date4, date5);

    private JdbcTournamentDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTournamentDao(jdbcTemplate);
        sut.createTournament(TOURNAMENT1);
        sut.createTournament(TOURNAMENT2);
        sut.createTournament(TOURNAMENT3);
    }

    @Test
    public void showAllTournamentsLists3Tournaments()    {
        List <Tournament> tournaments = sut.getAllTournaments();
        int expectedSize = 3;
        Assert.assertEquals(6, tournaments.size());
    }

    @Test
    public void showPASTTournamentsCount1()  {
        List <Tournament> tournaments = sut.getPastTournaments();
        int size = 1;
        Assert.assertEquals(1, tournaments.size());
    }

}
