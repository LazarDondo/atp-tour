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

    private Country testCountry;
    private Tournament testTournament;
    private Player firstTestPlayer;
    private Player secondTestPlayer;
    private Match testMatch;
    private Statistics testStatistics;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private StatisticsDao statisticsDao;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, 0, "Serbia", "SRB"));
        testTournament = tournamentDao.save(new Tournament(1, 0, "Wimbledon-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 28),
                testCountry, "Grand Slam", null, null, null));
        firstTestPlayer = playerDao.save(new Player(1, 0, "Novak", "Djokovic", testCountry, LocalDate.of(1987, Month.MAY, 22),
                12000, 12000, 1, null, null));
        secondTestPlayer = playerDao.save(new Player(2, 0, "Filip", "Krajinovic", testCountry,
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2, null, null));
        testMatch = matchDao.save(new Match(testTournament, firstTestPlayer, secondTestPlayer, 0, LocalDate.of(2022, Month.OCTOBER, 22),
                "finals", "3-2", firstTestPlayer));
        testStatistics = statisticsDao.save(new Statistics(1, testMatch, 0, 60, 30, 10, 5, 6, 2, 35, 33, 23, 10));
    }

    @Test
    public void saveStatisticsShouldBeOk() {
        Tournament tournament = tournamentDao.save(new Tournament(1, 0, "Roland Garros-2020", LocalDate.of(2020, Month.MAY, 11), LocalDate.of(2020, Month.MAY, 17),
                testCountry, "Grand Slam", null, null, null));
        Match match = matchDao.save(new Match(tournament, firstTestPlayer, secondTestPlayer, 0, LocalDate.of(2022, Month.MAY, 11),
                "finals", "3-2", firstTestPlayer));
        Statistics statistics = new Statistics(1, match, 0, 60, 30, 10, 5, 6, 2, 35, 33, 23, 10);
        assertEquals(statistics, statisticsDao.save(statistics));
    }

    @Test
    public void findStatisticsByMatchShouldBeOk() {
        assertEquals(testStatistics, statisticsDao.findStatisticsByMatch(testMatch).get());
    }

    @Test
    public void findStatisticsByMatchShouldBeEmpty() {
        Player player = playerDao.save(new Player(55));
        Match match = matchDao.save(new Match(testTournament, player, secondTestPlayer, LocalDate.of(2022, Month.OCTOBER, 22), "3-2"));
        assertEquals(true, statisticsDao.findStatisticsByMatch(match).isEmpty());
    }
}
