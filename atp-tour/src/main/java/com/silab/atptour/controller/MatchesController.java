package com.silab.atptour.controller;

import com.silab.atptour.entity.Match;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.MatchesService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("matches")
public class MatchesController {

    private final Logger logger = LoggerFactory.getLogger(MatchesController.class);

    @Autowired
    MatchesService matchesService;

    @PostMapping
    public ResponseEntity<List<Match>> addMatches(@RequestBody List<Match> matches) {
        logger.info("Adding {} matches", matches.size());
        List<Match> addedMatches = matchesService.addMatches(matches);
        logger.info("Successfully added {} matches", addedMatches.size());
        return ResponseEntity.ok(addedMatches);
    }

    @PutMapping
    public ResponseEntity<List<Match>> updateMatches(@RequestBody List<Match> matches) {
        logger.info("Updating {} matches", matches.size());
        List<Match> updatedMatches = matchesService.updateMatches(matches);
        logger.info("Successfully updated {} matches", updatedMatches.size());
        return ResponseEntity.ok(updatedMatches);
    }

    @GetMapping("{firstPlayerId}/{secondPlayerId}")
    public ResponseEntity<List<Match>> getH2HMatches(@PathVariable("firstPlayerId") long firstPlayerId,
            @PathVariable("secondPlayerId") long secondPlayerId) {
        logger.debug("Finding H2H matches between players with ids {} and {}", firstPlayerId, secondPlayerId);
        try {
            List<Match> foundMatches = matchesService.getH2HMatches(firstPlayerId, secondPlayerId);
            logger.info("Successfully retrieved {} matches", foundMatches.size());
            return ResponseEntity.ok(foundMatches);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
