package com.silab.atptour.controller;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Statistics;
import com.silab.atptour.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for statistics data management
 * 
 * @author Lazar
 */
@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    /**
     * PUT request for adding new statistics to the database
     *
     * @param statistics A {@link Statistics} object to be added to the database
     *
     * @return A {@link ResponseEntity} instance with the added statistics and OK HTTP status
     */
    @PutMapping
    public ResponseEntity<Statistics> saveStatistics(@RequestBody Statistics statistics) {
        logger.info("Saving new statistics");
        Statistics savedStatistics = statisticsService.saveStatistics(statistics);
        return ResponseEntity.ok(savedStatistics);
    }

    /**
     * POST request for finding match statistics
     * 
     * @param match A {@link Match} object for which statistics need to be found
     * 
     * @return A {@link ResponseEntity} instance with the found statistics
     * 
     */
    @PostMapping("/find")
    public ResponseEntity<Statistics> findStatistics(@RequestBody Match match) {
        logger.debug("Finding statistics for match between {} and {} on {}",
                match.getFirstPlayer(), match.getSecondPlayer(), match.getTournament().getName());
        return ResponseEntity.ok(statisticsService.findStatistics(match));
    }
}
