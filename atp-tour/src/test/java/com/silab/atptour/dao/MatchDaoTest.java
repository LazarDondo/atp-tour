package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class MatchDaoTest {

    @Autowired
    CountryDao countryDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    MatchDao matchDao;

    private Country testCountry;
    private Tournament testTournament;
    private Player firstTestPlayer;
    private Player secondTestPlayer;
    private Match testMatch;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, "Serbia", "SRB"));
        testTournament = tournamentDao.save(new Tournament(1, "Wimbledon-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 30),
                testCountry, "Grand Slam", null));
        firstTestPlayer = playerDao.save(new Player(1, "Novak", "Djokovic", testCountry, LocalDate.of(1987, Month.MAY, 22), 12000, 12000, 1, null));
        secondTestPlayer = playerDao.save(new Player(2, "Filip", "Krajinovic", testCountry, LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2, null));
        testMatch = matchDao.save(new Match(testTournament, firstTestPlayer, secondTestPlayer, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "3-2", firstTestPlayer));
    }

    @Test
    public void saveMatchShouldBeOk() {
        Player player = playerDao.save(new Player(3, "Miomir", "Kecmanovic", testCountry, LocalDate.of(1999, Month.AUGUST, 31), 5000, 5000, 3, null));
        Match match = new Match(testTournament, firstTestPlayer, player, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "3-2", firstTestPlayer);
        assertEquals(match, matchDao.save(match));
    }

    @Test
    public void updateMatchShouldBeOk() {
        Player player = new Player(3, "Miomir", "Kecmanovic", testCountry, LocalDate.of(1999, Month.AUGUST, 31), 5000, 5000, 3, null);
        testMatch.setSecondPlayer(player);
        assertEquals(testMatch, matchDao.save(testMatch));
    }

    @Test
    public void findH2HMatchesShouldBeOk() {
        Tournament tournament = tournamentDao.save(new Tournament(1, "Roland Garros-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 30),
                testCountry, "Grand Slam", null));
        testMatch = matchDao.save(new Match(tournament, firstTestPlayer, secondTestPlayer, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "2-3", secondTestPlayer));
        assertEquals(2, matchDao.findByFirstPlayerAndSecondPlayer(firstTestPlayer, secondTestPlayer).size());
    }

    @Test
    public void findH2HMatchesShouldReturnEmptyList() {
        Player player = new Player(3, "Miomir", "Kecmanovic", testCountry, LocalDate.of(1999, Month.AUGUST, 31), 5000, 5000, 3, null);
        assertEquals(0, matchDao.findByFirstPlayerAndSecondPlayer(firstTestPlayer, player).size());
    }

}
