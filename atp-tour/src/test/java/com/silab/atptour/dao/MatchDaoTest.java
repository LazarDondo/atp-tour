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
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class MatchDaoTest {

    private Country testCountry;
    private Tournament testTournament;
    private Player firstTestPlayer;
    private Player secondTestPlayer;
    private Match testMatch;
    private Pageable pageable;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private MatchDao matchDao;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, 0, "Serbia", "SRB"));

        testTournament = tournamentDao.save(new Tournament(1, 0, "Wimbledon-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 28),
                testCountry, "Grand Slam", null, null, null));
        firstTestPlayer = playerDao.save(new Player(1, 0, "Novak", "Djokovic", testCountry, LocalDate.of(1987, Month.MAY, 22), 12000,
                12000, 1, null, null));
        secondTestPlayer = playerDao.save(new Player(2, 0, "Filip", "Krajinovic", testCountry, LocalDate.of(1992, Month.SEPTEMBER, 27),
                10000, 10000, 2, null, null));
        testMatch = matchDao.save(new Match(testTournament, firstTestPlayer, secondTestPlayer, 0, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "3-2", firstTestPlayer));

        Tournament tournament = tournamentDao.save(new Tournament(1, 0, "Roland Garros-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 28),
                testCountry, "Grand Slam", null, null, null));
        matchDao.save(new Match(tournament, firstTestPlayer, secondTestPlayer, 0, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "2-3", secondTestPlayer));

        Player player = playerDao.save(new Player(3, 0, "Miomir", "Kecmanovic", testCountry,
                LocalDate.of(1999, Month.AUGUST, 31), 5000, 5000, 3, null, null));
        matchDao.save(new Match(testTournament, firstTestPlayer, player, 0, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "2-3", secondTestPlayer));
        matchDao.save(new Match(testTournament, secondTestPlayer, player, 0, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "2-3", secondTestPlayer));
        pageable = Pageable.ofSize(Integer.MAX_VALUE);
    }

    @Test
    public void saveMatchShouldBeOk() {
        Player player = playerDao.save(new Player(3, 0, "Miomir", "Kecmanovic", testCountry, LocalDate.of(1999, Month.AUGUST, 31),
                5000, 5000, 3, null, null));
        Match match = new Match(testTournament, firstTestPlayer, player, 0, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "3-2", firstTestPlayer);
        assertEquals(match, matchDao.save(match));
    }

    @Test
    public void updateMatchShouldBeOk() {
        Player player = new Player(3, 0, "Miomir", "Kecmanovic", testCountry, LocalDate.of(1999, Month.AUGUST, 31),
                5000, 5000, 3, null, null);
        testMatch.setSecondPlayer(player);
        assertEquals(testMatch, matchDao.save(testMatch));
    }

    @Test
    public void filterMatchesShouldBeOk() {
        assertEquals(1, matchDao.filterMatches(testTournament, firstTestPlayer, secondTestPlayer, pageable).getNumberOfElements());
    }

    @Test
    public void filterAllMatchesBetweenPlayersShouldBeOk() {
        assertEquals(2, matchDao.filterMatches(null, firstTestPlayer, secondTestPlayer, pageable).getNumberOfElements());
    }

    @Test
    public void filterFirstPlayerMatchesShouldBeOk() {
        assertEquals(3, matchDao.filterMatches(null, firstTestPlayer, null, pageable).getNumberOfElements());
    }

    @Test
    public void filterFirstPlayerMatchesOnTournamentShouldBeOk() {
        assertEquals(2, matchDao.filterMatches(testTournament, firstTestPlayer, null, pageable).getNumberOfElements());
    }

    @Test
    public void filterSecondPlayerMatchesShouldBeOk() {

        assertEquals(3, matchDao.filterMatches(null, null, secondTestPlayer, pageable).getNumberOfElements());
    }

    @Test
    public void filterSecondPlayerMatchesOnTournamentShouldBeOk() {
        assertEquals(2, matchDao.filterMatches(testTournament, null, secondTestPlayer, pageable).getNumberOfElements());
    }

    @Test
    public void filterAllMatchesShouldBeOk() {
        assertEquals(4, matchDao.filterMatches(null, null, null, pageable).getNumberOfElements());
    }

    @Test
    public void filterMatchesShouldBeEmpty() {
        Player player = playerDao.save(new Player(4, 0, "Laslo", "Djere", testCountry,
                LocalDate.of(1996, Month.JUNE, 20), 4000, 4000, 4, null, null));
        matchDao.save(new Match(testTournament, secondTestPlayer, player, 0, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "2-3", secondTestPlayer));
        assertEquals(0, matchDao.filterMatches(testTournament, firstTestPlayer, player, pageable).getNumberOfElements());
    }

}
