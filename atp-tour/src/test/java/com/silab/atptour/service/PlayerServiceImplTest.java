package com.silab.atptour.service;

import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.EntityNotFoundException;
import com.silab.atptour.service.impl.PlayerServiceImpl;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Lazar
 */
@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    @Mock
    PlayerDao playerDao;

    @InjectMocks
    PlayerServiceImpl playerService;

    private static Player testPlayer;
    private static Optional<Player> optionalPlayer;
    private static Optional<Player> emptyOptionalPlayer;

    @BeforeAll
    public static void init() {
        testPlayer = new Player(1, "Novak", "Djokovic", new Country(1, "Serbia", "SRB"),
                LocalDate.of(2022, Month.MAY, 22), 12000, 12000, null);
        optionalPlayer = Optional.of(testPlayer);
        emptyOptionalPlayer = Optional.empty();
    }

    @Test
    public void addPlayerShouldBeOk() {
        when(playerDao.save(testPlayer)).thenReturn(testPlayer);
        assertEquals(testPlayer, playerService.addPlayer(testPlayer));
    }

    @Test
    public void updatePlayerShouldBeOk() throws EntityNotFoundException {
        when(playerDao.findById(testPlayer.getId())).thenReturn(optionalPlayer);
        when(playerDao.save(testPlayer)).thenReturn(testPlayer);
        assertEquals(testPlayer, playerService.updatePlayer(testPlayer));
    }

    @Test
    public void updatePlayerShouldThrowEntityNotFoundException() {
        when(playerDao.findById(testPlayer.getId())).thenReturn(emptyOptionalPlayer);
        Assertions.assertThrows(EntityNotFoundException.class, () -> playerService.updatePlayer(testPlayer));
    }

    @Test
    public void getPlayerShouldBeOk() throws EntityNotFoundException {
        when(playerDao.findById(testPlayer.getId())).thenReturn(optionalPlayer);
        assertEquals(testPlayer, playerService.getPlayer(testPlayer.getId()));
    }

    @Test
    public void getPlayerShouldThrowEntityNotFoundException() {
        when(playerDao.findById(testPlayer.getId())).thenReturn(emptyOptionalPlayer);
        Assertions.assertThrows(EntityNotFoundException.class, () -> playerService.getPlayer(testPlayer.getId()));
    }

    @Test
    public void getAllPlayersShouldBeOk() {
        Player player = new Player(2, "Filip", "Krajinovic", new Country(1, "Serbia", "SRB"),
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, null);
        List<Player> players = new ArrayList<>();
        players.add(testPlayer);
        players.add(player);
        when(playerDao.findAll()).thenReturn(players);
        assertEquals(players, playerService.getAllPlayers());
    }

    @Test
    public void getMatchesShouldBeOk() throws EntityNotFoundException {
        Tournament tournament = new Tournament(1);
        Player firstPlayer = new Player(1);
        Player secondPlayer = new Player(2);
        List<Match> matches = new ArrayList<>() {
            {
                new Match(tournament, testPlayer, secondPlayer, LocalDate.of(2022, Month.MAY, 24), "first-round", "3-1", testPlayer);
                new Match(tournament, testPlayer, firstPlayer, LocalDate.of(2022, Month.MAY, 24), "semi-finals", "3-0", testPlayer);
            }
        };
        testPlayer.setMatches(matches);
        when(playerDao.findById(testPlayer.getId())).thenReturn(optionalPlayer);
        assertEquals(matches, playerService.getMatches(testPlayer.getId()));
    }

    @Test
    public void getMatchesShouldThrowEntityNotFoundException() {
        when(playerDao.findById(testPlayer.getId())).thenReturn(emptyOptionalPlayer);
        Assertions.assertThrows(EntityNotFoundException.class, () -> playerService.getMatches(testPlayer.getId()));
    }
}
