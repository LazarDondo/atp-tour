package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Tournament;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class TournamentDaoTest {

    private Tournament testTournament;
    private Country testCountry;
    private Pageable pageable;

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private CountryDao countryDao;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, "England", "ENG"));
        testTournament = tournamentDao.save(new Tournament(1, "Wimbledon-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 28),
                testCountry, "Grand Slam", null, null, null));
        pageable = Pageable.ofSize(Integer.MAX_VALUE);
    }

    @Test
    public void addTournamentShouldBeOk() {
        Tournament tournament = (new Tournament(1, "Roland Garros-2020", LocalDate.of(2020, Month.APRIL, 22), LocalDate.of(2020, Month.APRIL, 28),
                testCountry, "Grand Slam", null, null, null));
        assertEquals(tournament, tournamentDao.save(tournament));
    }

    @Test
    public void findTournamentByIdShouldBeOk() {
        assertEquals(testTournament, tournamentDao.findById(testTournament.getId()).get());
    }

    @Test
    public void findTournamentByIdShouldNotFindTournament() {
        assertEquals(true, tournamentDao.findById(testTournament.getId() + 1).isEmpty());
    }

    @Test
    public void findTournamentByNameShouldBeOk() {
        assertEquals(testTournament, tournamentDao.findTournamentByName(testTournament.getName()).get());
    }

    @Test
    public void findTournamentByNameShouldNotFindTournament() {
        assertEquals(true, tournamentDao.findTournamentByName("Shangai-2021").isEmpty());
    }
    
    @Test
    public void findTournamentByStartDateShouldBeOk(){
        tournamentDao.save(new Tournament(1, "Roland Garros-2020", LocalDate.of(2020, Month.MARCH, 22), LocalDate.of(2020, Month.MARCH, 28),
                testCountry, "Grand Slam", null, null, null));
        assertEquals(2, tournamentDao.findTournamentByStartDate(testTournament.getStartDate()).size());
    }
    
    @Test
    public void findTournamentByStartDateShouldBeNotFindTournaments(){
        assertEquals(true, tournamentDao.findTournamentByStartDate(testTournament.getStartDate().plusDays(55)).isEmpty());
    }

    @Test
    public void findAllTournamentsShouldBeOk() {
        tournamentDao.save((new Tournament(1, "Roland Garros-2020", LocalDate.of(2020, Month.APRIL, 22),
                LocalDate.of(2020, Month.APRIL, 28), testCountry, "Grand Slam", null, null, null)));
        assertEquals(2, tournamentDao.findAllTournaments("d", null, "Grand Slam", pageable).getNumberOfElements());
        assertEquals(1, tournamentDao.findAllTournaments("rol", null, "Grand Slam", pageable).getNumberOfElements());
        assertEquals(1, tournamentDao.findAllTournaments(null, testCountry.getName(), null, pageable).getNumberOfElements());
    }

    @Test
    public void updateTournamentShouldBeOk() {
        testTournament.setName("Australian Open-2020");
        assertEquals(testTournament, tournamentDao.save(testTournament));
    }

    @Test
    public void deleteTournamentByIdShouldBeOk() {
        tournamentDao.deleteById(testTournament.getId());
        assertEquals(true, tournamentDao.findById(testTournament.getId()).isEmpty());
    }

    @Test
    public void deleteTournamentByIdShouldThrowEmptyResultDataAccessException() {
        Assertions.assertThrows(EmptyResultDataAccessException.class,
                () -> tournamentDao.deleteById(testTournament.getId() + 1));
    }
}
