package com.silab.atptour.service;

import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
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
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Lazar
 */
@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {

    private static Player testPlayer;
    private static Optional<Player> optionalPlayer;
    private static Optional<Player> emptyOptionalPlayer;
    private static Pageable pageable;
    
    @Mock
    private PlayerDao playerDao;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeAll
    public static void init() {
        testPlayer = new Player(1, "Novak", "Djokovic", new Country(1, "Serbia", "SRB"),
                LocalDate.of(2022, Month.MAY, 22), 12000, 12000, 1, null, null);
        optionalPlayer = Optional.of(testPlayer);
        emptyOptionalPlayer = Optional.empty();
        pageable = Pageable.ofSize(Integer.MAX_VALUE);
    }

    @Test
    public void addPlayerShouldBeOk() {
        when(playerDao.save(testPlayer)).thenReturn(testPlayer);
        assertEquals(testPlayer, playerService.addPlayer(testPlayer));
    }

    @Test
    public void updatePlayerShouldBeOk() throws AtpEntityNotFoundException {
        when(playerDao.findById(testPlayer.getId())).thenReturn(optionalPlayer);
        when(playerDao.save(testPlayer)).thenReturn(testPlayer);
        assertEquals(testPlayer, playerService.updatePlayer(testPlayer));
    }

    @Test
    public void updatePlayerShouldThrowAtpEntityNotFoundException() {
        when(playerDao.findById(testPlayer.getId())).thenReturn(emptyOptionalPlayer);
        Assertions.assertThrows(AtpEntityNotFoundException.class, () -> playerService.updatePlayer(testPlayer));
    }

    @Test
    public void getPlayerShouldBeOk() throws AtpEntityNotFoundException {
        when(playerDao.findById(testPlayer.getId())).thenReturn(optionalPlayer);
        assertEquals(testPlayer, playerService.getPlayer(testPlayer.getId()));
    }

    @Test
    public void getPlayerShouldThrowAtpEntityNotFoundException() {
        when(playerDao.findById(testPlayer.getId())).thenReturn(emptyOptionalPlayer);
        Assertions.assertThrows(AtpEntityNotFoundException.class, () -> playerService.getPlayer(testPlayer.getId()));
    }
}
