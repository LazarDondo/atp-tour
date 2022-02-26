package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Income;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.entity.id.IncomeId;
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
public class IncomeDaoTest {

    private Income testIncome;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private IncomeDao incomeDao;

    @BeforeEach
    public void init() {
        Country country = countryDao.save(new Country(1, "Serbia", "SRB"));

        Tournament tournament = tournamentDao.save(new Tournament(1, "Wimbledon-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 28),
                country, "Grand Slam", null, null, null));
        Player firstPlayer = playerDao.save(new Player(1, "Novak", "Djokovic", country, LocalDate.of(1987, Month.MAY, 22), 12000,
                12000, 1, null, null));
        testIncome = incomeDao.save(new Income(tournament, firstPlayer, 100));
    }

    @Test
    public void saveIncomeShouldBeOk() {
        testIncome.setPoints(300);
        assertEquals(testIncome, incomeDao.save(testIncome));
    }

    @Test
    public void findByIdShouldBeOk() {
        IncomeId incomeId = new IncomeId(testIncome.getTournament().getId(), testIncome.getPlayer().getId());
        assertEquals(testIncome, incomeDao.findById(incomeId).get());
    }

    @Test
    public void findByIdShouldNotFindIncome() {
        assertEquals(true, incomeDao.findById(new IncomeId(55, 55)).isEmpty());
    }
}
