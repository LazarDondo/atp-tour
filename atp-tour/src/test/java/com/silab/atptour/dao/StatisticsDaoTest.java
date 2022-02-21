package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Statistics;
import com.silab.atptour.entity.Tournament;
import java.time.LocalDate;
import java.time.Month;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class StatisticsDaoTest {
    
    @Autowired
    CountryDao countryDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    MatchDao matchDao;
    
    @Autowired
    StatisticsDao statisticsDao;

    private Country testCountry;
    private Tournament testTournament;
    private Player firstTestPlayer;
    private Player secondTestPlayer;
    private Match testMatch;
    private Statistics testStatistics;
    
    @BeforeEach
    public void init(){
        testCountry = countryDao.save(new Country(1, "Serbia", "SRB"));
        testTournament = tournamentDao.save(new Tournament(1, "Wimbledon-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 28),
                testCountry, "Grand Slam", null, null, null));
        firstTestPlayer = playerDao.save(new Player(1, "Novak", "Djokovic", testCountry, LocalDate.of(1987, Month.MAY, 22), 
                12000, 12000, 1, null, null));
        secondTestPlayer = playerDao.save(new Player(2, "Filip", "Krajinovic", testCountry,
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2, null, null));
        testMatch = matchDao.save(new Match(testTournament, firstTestPlayer, secondTestPlayer, LocalDate.of(2022, Month.OCTOBER, 22),
                "finals", "3-2", firstTestPlayer));
        testStatistics = statisticsDao.save(new Statistics(1, testMatch, 60, 30, 10, 5, 6, 2, 35, 33, 23, 10));
    }
    
    @Test
    public void addStatisticsShouldBeOk(){
        Tournament tournament = tournamentDao.save(new Tournament(1, "Roland Garros-2020", LocalDate.of(2020, Month.MAY, 11), LocalDate.of(2020, Month.MAY, 17),
                testCountry, "Grand Slam", null, null, null));
        Match match = matchDao.save(new Match(tournament, firstTestPlayer, secondTestPlayer, LocalDate.of(2022, Month.MAY, 11),
                "finals", "3-2", firstTestPlayer));
        Statistics statistics = new Statistics(1, match, 60, 30, 10, 5, 6, 2, 35, 33, 23, 10);
        assertEquals(statistics, statisticsDao.save(statistics));
    }
    
    @Test
    public void updateStatisticsShouldBeOk(){
        testStatistics.setFirstPlayerPoints(3);
        assertEquals(testStatistics, statisticsDao.save(testStatistics));
    }
}
