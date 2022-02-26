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

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class PlayerDaoTest {

    private Country testCountry;
    private Player testPlayer;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private PlayerDao playerDao;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, "Serbia", "SRB"));
        testPlayer = playerDao.save(new Player(1, "Novak", "Djokovic", testCountry,
                LocalDate.of(1987, Month.MAY, 22), 12000, 12000, 1, null, null));
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
        assertEquals(players, playerDao.findAllRankedPlayers());
    }

    @Test
    public void updatePlayerShouldBeOk() {
        testPlayer.setCurrentPoints(11111);
        assertEquals(testPlayer, playerDao.save(testPlayer));
    }
}
