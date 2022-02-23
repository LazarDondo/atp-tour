package com.silab.atptour.controller;

import com.silab.atptour.entity.Match;
import com.silab.atptour.service.MatchesService;
import java.util.List;
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
@RequestMapping("matches")
public class MatchesController {

    private final Logger logger = LoggerFactory.getLogger(MatchesController.class);

    @Autowired
    MatchesService matchesService;

    @PutMapping
    public ResponseEntity<List<Match>> updateMatches(@RequestBody List<Match> matches) {
        logger.info("Updating {} matches", matches.size());
        List<Match> updatedMatches = matchesService.updateMatches(matches);
        logger.info("Successfully updated {} matches", matches.size());
        return ResponseEntity.ok(updatedMatches);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Match>> filterMatches(@RequestBody Match searchValues) {
        logger.debug("Finding matches");
        List<Match> foundMatches = matchesService.filterMatches(searchValues.getTournament(),
                searchValues.getFirstPlayer(), searchValues.getSecondPlayer());
        logger.info("Successfully retrieved {} matches", foundMatches.size());
        return ResponseEntity.ok(foundMatches);
    }
}
