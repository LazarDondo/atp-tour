package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Player;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
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
public class PlayerDaoTest {

    private Country testCountry;
    private Player testPlayer;
    private Pageable pageable;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private PlayerDao playerDao;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, "Serbia", "SRB"));
        testPlayer = playerDao.save(new Player(1, "Novak", "Djokovic", testCountry,
                LocalDate.of(1987, Month.MAY, 22), 12000, 12000, 1, null, null));
        pageable = Pageable.ofSize(Integer.MAX_VALUE);
    }

    @Test
    public void findPlayerByIdShouldBeOk() {
        assertEquals(testPlayer, playerDao.findById(testPlayer.getId()).get());
    }

    @Test
    public void findPlayerByIdShouldNotFindPlayer() {
        assertEquals(true, playerDao.findById(testPlayer.getId() + 1).isEmpty());
    }

    @Test
    public void findAllRankedPlayersShouldBeOk() {
        Player player = playerDao.save(new Player(2, "Filip", "Krajinovic", testCountry,
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2, null, null));
        playerDao.save(new Player(3, "Test", "Test", testCountry,
                LocalDate.of(1995, Month.JANUARY, 12), 0, 0, 0, null, null));
        List<Player> players = new ArrayList<>();
        players.add(testPlayer);
        players.add(player);
        assertEquals(players, playerDao.findAllRankedPlayers(null, null, null, pageable).getContent());
        assertEquals(players.size()-1, playerDao.findAllRankedPlayers(null, "c", testCountry.getName(), pageable).getNumberOfElements());
        assertEquals(testPlayer, playerDao.findAllRankedPlayers("Novak", null, testCountry.getName(), pageable).getContent().get(0));
        assertEquals(players.size(), playerDao.findAllRankedPlayers(null, null, testCountry.getName(), pageable).getNumberOfElements());
    }

    @Test
    public void updatePlayerShouldBeOk() {
        testPlayer.setCurrentPoints(11111);
        assertEquals(testPlayer, playerDao.save(testPlayer));
    }
}
