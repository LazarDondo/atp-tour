package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Player;
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
public class PlayerDaoTest {
    
    @Autowired
    CountryDao countryDao;
    
    @Autowired
    PlayerDao playerDao;
    
    private Country testCountry;
    private Player testPlayer;
    
    @BeforeEach
    public void init(){
        testCountry=countryDao.save(new Country(1, "Serbia", "SRB"));
        testPlayer=playerDao.save(new Player(1, "Novak", "Djokovic", testCountry, LocalDate.of(1987, Month.MAY, 22), 12000, 12000, null));
    }
    
    @Test
    public void findPlayerByIdShouldBeOk(){
        assertEquals(testPlayer, playerDao.findById(testPlayer.getId()).get());
    }
    
     @Test
    public void findPlayerByIdShouldNotFindPlayer(){
        assertEquals(true, playerDao.findById(testPlayer.getId()+1).isEmpty());
    }
    
    @Test
    public void updatePlayerShouldBeOk(){
        testPlayer.setCurrentPoints(11111);
        assertEquals(testPlayer, playerDao.save(testPlayer));
    }
}
