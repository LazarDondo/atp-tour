package com.silab.atptour.service;

import com.silab.atptour.dao.StatisticsDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Statistics;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.impl.StatisticsServiceImpl;
import java.time.LocalDate;
import java.time.Month;
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
public class StatisticsServiceImplTest {
    
    @Mock
    StatisticsDao statisticsDao;
    
    @InjectMocks
    StatisticsServiceImpl statisticsService;
    
    private static Statistics testStatistics;
    private static Optional<Statistics> optionalStatistics;
    private static Optional<Statistics> emptyStatistics;
    
    @BeforeAll
    public static void init(){
        Player player = new Player(1, 1);
        Match match = new Match(new Tournament(1), player, new Player(2, 2), 
                LocalDate.of(2022, Month.MARCH, 8), "finals", "2-0", player);
        testStatistics = new Statistics(1, match, 50, 35, 10, 3, 5, 1, 33, 25, 33, 20);
        optionalStatistics=Optional.of(testStatistics);
        emptyStatistics=Optional.empty();
    }
    
    @Test
    public void addStatisticsShouldBeOk(){
        when(statisticsDao.save(testStatistics)).thenReturn(testStatistics);
        assertEquals(testStatistics, statisticsService.addStatistics(testStatistics));
    }
    
    @Test
    public void updateStatisticsShouldBeOk() throws AtpEntityNotFoundException{
        when(statisticsDao.findStatisticsById(testStatistics.getId())).thenReturn(optionalStatistics);
        when(statisticsDao.save(testStatistics)).thenReturn(testStatistics);
        assertEquals(testStatistics, statisticsService.updateStatistics(testStatistics));
    }
    
    @Test
    public void updateStatisticsShouldAtpThrowEntityNotFoundException(){
        when(statisticsDao.findStatisticsById(testStatistics.getId())).thenReturn(emptyStatistics);
        Assertions.assertThrows(AtpEntityNotFoundException.class, ()->statisticsService.updateStatistics(testStatistics));
    }
}
