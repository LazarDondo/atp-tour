package com.silab.atptour.service;

import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.impl.MatchesServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    private static Tournament testTournament;
    private static Player firstTestPlayer;
    private static Player secondTestPlayer;
    private static Match testMatch;

    @Mock
    private MatchDao matchDao;

    @InjectMocks
    private MatchesServiceImpl matchService;

    @BeforeAll
    public static void init() {
        testTournament = new Tournament(1);
        firstTestPlayer = new Player(1);
        secondTestPlayer = new Player(2);
        testMatch = new Match(testTournament, firstTestPlayer, secondTestPlayer, LocalDate.now(), "finals");
    }
}
