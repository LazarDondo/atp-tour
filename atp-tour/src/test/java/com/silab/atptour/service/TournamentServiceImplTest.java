package com.silab.atptour.service;

import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.impl.TournamentServiceImpl;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Lazar
 */
@ExtendWith(MockitoExtension.class)
public class TournamentServiceImplTest {

    private static Tournament testTournament;
    private static Optional<Tournament> optionalTournament;
    private static Optional<Tournament> emptyOptionalTournament;

    @Mock
    private TournamentDao tournamentDao;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @BeforeAll
    public static void init() {
        testTournament = new Tournament(1, "Wimbledon", LocalDate.of(2022, Month.JULY, 10),
                LocalDate.of(2022, Month.JULY, 16), new Country(1, "Great Britain", "GBR"), "Grand Slam", null, null, null);
        optionalTournament = Optional.of(testTournament);
        emptyOptionalTournament = Optional.empty();
    }

    @Test
    public void addTournamentShouldBeOk() throws AtpEntityExistsException {
        when(tournamentDao.findTournamentByName(testTournament.getName() + "-" + testTournament.getStartDate().getYear())).thenReturn(emptyOptionalTournament);
        when(tournamentDao.save(testTournament)).thenReturn(testTournament);
        assertEquals(testTournament, tournamentService.addTournament(testTournament));
    }

    @Test
    public void addTournamentShouldThrowAtpEntityExistsException() {
        when(tournamentDao.findTournamentByName(testTournament.getName() + "-" + testTournament.getStartDate().getYear())).thenReturn(optionalTournament);
        Assertions.assertThrows(AtpEntityExistsException.class, () -> tournamentService.addTournament(testTournament));
    }

    @Test
    public void updateTournamentShouldBeOk() throws AtpEntityNotFoundException, AtpEntityExistsException {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(optionalTournament);
        when(tournamentDao.save(testTournament)).thenReturn(testTournament);
        assertEquals(testTournament, tournamentService.updateTournament(testTournament));
    }

    @Test
    public void updateTournamentShouldThrowAtpEntityNotFoundException() {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(emptyOptionalTournament);
        Assertions.assertThrows(AtpEntityNotFoundException.class, () -> tournamentService.updateTournament(testTournament));
    }

    @Test
    public void updateTournamentShouldThrowAtpEntityExistsException() {
        Tournament tournament = new Tournament(1L);
        tournament.setName("Roland Garros");
        tournament.setStartDate(LocalDate.of(2022, Month.MAY, 10));
        
        when(tournamentDao.findById(testTournament.getId())).thenReturn(optionalTournament);
        when(tournamentDao.findTournamentByName(tournament.getName() + "-" + tournament.getStartDate().getYear())).thenReturn(Optional.of(tournament));
        Assertions.assertThrows(AtpEntityExistsException.class, () -> tournamentService.updateTournament(tournament));
    }

    @Test
    public void getTournamentShouldBeOk() throws AtpEntityNotFoundException {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(optionalTournament);
        assertEquals(testTournament, tournamentService.getTournament(testTournament.getId()));
    }

    @Test
    public void getTournamentShouldThrowAtpEntityNotFoundException() {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(emptyOptionalTournament);
        Assertions.assertThrows(AtpEntityNotFoundException.class, () -> tournamentService.getTournament(testTournament.getId()));
    }

    @Test
    public void getAllTournamentsShouldBeOk() {
        Tournament tournament = new Tournament(1, "Roland Garros-2022", LocalDate.of(2022, Month.MAY, 10),
                LocalDate.of(2022, Month.MAY, 17), new Country(1, "France", "FRA"), "Grand Slam", null, null, null);
        List<Tournament> tournaments = new ArrayList<>();
        tournaments.add(tournament);
        tournaments.add(testTournament);
        
        when(tournamentDao.findAll()).thenReturn(tournaments);
        assertEquals(tournaments, tournamentService.getAllTournaments());
    }

    @Test
    public void deleteTournamentShouldAtpThrowEntityNotFoundException() {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(emptyOptionalTournament);
        Assertions.assertThrows(AtpEntityNotFoundException.class, () -> tournamentService.deleteTournament(testTournament.getId()));
    }
}
