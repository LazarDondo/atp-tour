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
 *
 * @author Lazar
 */
@RestController
@RequestMapping("statistics")
public class StatisticsController {

    private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    StatisticsService statisticsService;

    @PutMapping
    public ResponseEntity<Statistics> saveStatistics(@RequestBody Statistics statistics) {
        logger.info("Saving new statistics");
        Statistics savedStatistics = statisticsService.saveStatistics(statistics);
        return ResponseEntity.ok(savedStatistics);
    }

    @PostMapping("/find")
    public ResponseEntity<Statistics> findStatistics(@RequestBody Match match) {
        logger.debug("Finding statistics for match between {} and {} on {}",
                match.getFirstPlayer(), match.getSecondPlayer(), match.getTournament().getName());
        return ResponseEntity.ok(statisticsService.findStatistics(match));
    }
}
