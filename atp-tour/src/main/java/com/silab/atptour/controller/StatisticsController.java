package com.silab.atptour.controller;

import com.silab.atptour.entity.Statistics;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
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

    @PostMapping
    public ResponseEntity<Statistics> addStatistics(@RequestBody Statistics statistics) {
        logger.info("Adding new statistics");
        Statistics addedStatistics = statisticsService.addStatistics(statistics);
        logger.info("Successfully added new statistics");
        return ResponseEntity.ok(addedStatistics);
    }

    @PutMapping
    public ResponseEntity<Statistics> updateStatistics(@RequestBody Statistics statistics) {
        logger.info("Updating statistics");
        try {
            Statistics updatedStatistics = statisticsService.updateStatistics(statistics);
            logger.info("Successfully updated statistics");
            return ResponseEntity.ok(updatedStatistics);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
