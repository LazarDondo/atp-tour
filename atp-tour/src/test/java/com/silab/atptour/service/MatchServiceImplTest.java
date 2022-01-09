package com.silab.atptour.service;

import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.EntityNotFoundException;
import com.silab.atptour.service.impl.MatchesServiceImpl;
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
public class MatchServiceImplTest {

    @Mock
    PlayerDao playerDao;

    @Mock
    MatchDao matchDao;

    @InjectMocks
    MatchesServiceImpl matchService;

    private static Player firstTestPlayer;
    private static Player secondTestPlayer;
    private static Match testMatch;
    private static List<Match> testMatches;

    @BeforeAll
    public static void init() {
        Tournament tournament = new Tournament(1);
        firstTestPlayer = new Player(1);
        secondTestPlayer = new Player(2);
        Player player = new Player(3);
        testMatches = new ArrayList<>() {
            {
                testMatch = new Match(tournament, firstTestPlayer, secondTestPlayer, LocalDate.of(2022, Month.MARCH, 1), "finals", "3-0", firstTestPlayer);
                new Match(tournament, firstTestPlayer, player, LocalDate.of(2022, Month.MARCH, 2), "finals", "3-0", firstTestPlayer);
                new Match(tournament, player, secondTestPlayer, LocalDate.of(2022, Month.MARCH, 3), "finals", "3-0", player);
            }
        };
    }

    @Test
    public void getMatchesH2HShouldBeOk() throws EntityNotFoundException {
        List<Match> matches = new ArrayList<>();
        matches.add(testMatch);
        when(playerDao.findById(firstTestPlayer.getId())).thenReturn(Optional.of(firstTestPlayer));
        when(playerDao.findById(secondTestPlayer.getId())).thenReturn(Optional.of(secondTestPlayer));
        when(matchDao.findByFirstPlayerAndSecondPlayer(firstTestPlayer, secondTestPlayer)).thenReturn(matches);
        assertEquals(matches, matchService.getH2HMatches(firstTestPlayer.getId(), secondTestPlayer.getId()));
    }

    @Test
    public void getMatchesH2HShouldThrowEntityNotFoundExceptionFirstPlayerNotFound() {
        when(playerDao.findById(firstTestPlayer.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> matchService.getH2HMatches(firstTestPlayer.getId(), secondTestPlayer.getId()));
    }

    @Test
    public void getMatchesH2HShouldThrowEntityNotFoundExceptionSecondPlayerNotFound() {
        when(playerDao.findById(firstTestPlayer.getId())).thenReturn(Optional.of(firstTestPlayer));
        when(playerDao.findById(secondTestPlayer.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> matchService.getH2HMatches(firstTestPlayer.getId(), secondTestPlayer.getId()));
    }
}
