package com.silab.atptour.service;

import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.EntityNotFoundException;
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

    @Mock
    TournamentDao tournamentDao;

    @InjectMocks
    TournamentServiceImpl tournamentService;

    private static Tournament testTournament;
    private static Optional<Tournament> optionalTournament;
    private static Optional<Tournament> emptyOptionalTournament;

    @BeforeAll
    public static void init() {
        testTournament = new Tournament(1, "Wimbledon-2022", LocalDate.of(2022, Month.JULY, 10),
                LocalDate.of(2022, Month.JULY, 24), new Country(1, "Great Britain", "GBR"), "Grand Slam", null);
        optionalTournament = Optional.of(testTournament);
        emptyOptionalTournament = Optional.empty();
    }

    @Test
    public void addTournamentShouldBeOk() {
        when(tournamentDao.save(testTournament)).thenReturn(testTournament);
        assertEquals(testTournament, tournamentService.addTournament(testTournament));
    }

    @Test
    public void updateTournamentShouldBeOk() throws EntityNotFoundException {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(optionalTournament);
        when(tournamentDao.save(testTournament)).thenReturn(testTournament);
        assertEquals(testTournament, tournamentService.updateTournament(testTournament));
    }

    @Test
    public void updateTournamentShouldThrowEntityNotFoundException() {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(emptyOptionalTournament);
        Assertions.assertThrows(EntityNotFoundException.class, () -> tournamentService.updateTournament(testTournament));
    }

    @Test
    public void getTournamentShouldBeOk() throws EntityNotFoundException {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(optionalTournament);
        assertEquals(testTournament, tournamentService.getTournament(testTournament.getId()));
    }

    @Test
    public void getTournamentShouldThrowEntityNotFoundException() {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(emptyOptionalTournament);
        Assertions.assertThrows(EntityNotFoundException.class, () -> tournamentService.getTournament(testTournament.getId()));
    }

    @Test
    public void getAllTournamentsShouldBeOk() {
        Tournament tournament = new Tournament(1, "Roland Garros-2022", LocalDate.of(2022, Month.MAY, 10),
                LocalDate.of(2022, Month.MAY, 24), new Country(1, "France", "FRA"), "Grand Slam", null);
        List<Tournament> tournaments = new ArrayList<>();
        tournaments.add(tournament);
        tournaments.add(testTournament);
        when(tournamentDao.findAll()).thenReturn(tournaments);
        assertEquals(tournaments, tournamentService.getAllTournaments());
    }

    @Test
    public void getMatchesShouldBeOk() throws EntityNotFoundException {
        Player firstPlayer = new Player(1);
        Player secondPlayer = new Player(2);
        Player thirdPlayer = new Player(3);
        List<Match> matches = new ArrayList<>() {
            {
                new Match(testTournament, firstPlayer, secondPlayer, LocalDate.of(2022, Month.MAY, 24), "first-round", "3-1", firstPlayer);
                new Match(testTournament, firstPlayer, thirdPlayer, LocalDate.of(2022, Month.MAY, 24), "semi-finals", "3-0", secondPlayer);
                new Match(testTournament, thirdPlayer, secondPlayer, LocalDate.of(2022, Month.MAY, 24), "finals", "2-3", secondPlayer);
            }
        };
        testTournament.setMatches(matches);
        when(tournamentDao.findById(testTournament.getId())).thenReturn(optionalTournament);
        assertEquals(matches, tournamentService.getMatches(testTournament.getId()));
    }

    @Test
    public void getMatchesShouldThrowEntityNotFoundException() {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(emptyOptionalTournament);
        Assertions.assertThrows(EntityNotFoundException.class, () -> tournamentService.getMatches(testTournament.getId()));
    }

    @Test
    public void deleteTournamentShouldThrowEntityNotFoundException() {
        when(tournamentDao.findById(testTournament.getId())).thenReturn(emptyOptionalTournament);
        Assertions.assertThrows(EntityNotFoundException.class, () -> tournamentService.deleteTournament(testTournament.getId()));
    }
}
