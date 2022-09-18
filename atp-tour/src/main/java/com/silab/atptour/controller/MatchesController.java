package com.silab.atptour.controller;

import com.silab.atptour.entity.Match;
import com.silab.atptour.model.PaginationModel;
import com.silab.atptour.service.MatchesService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for matches data management
 *
 * @author Lazar
 */
@RestController
@RequestMapping("matches")
@CrossOrigin(origins = "*")
public class MatchesController {

    @Autowired
    private MatchesService matchesService;

    private final Logger logger = LoggerFactory.getLogger(MatchesController.class);

    /**
     * PUT request for saving existing and new matches
     *
     * @param matches A {@link List} of {@link Match} entities
     *
     * @return A {@link ResponseEntity} instance with matches from the tournament and OK HTTP status
     */
    @PutMapping
    public ResponseEntity<Page<Match>> updateMatches(@RequestBody Match match, @PageableDefault(direction = Sort.Direction.ASC, page = PaginationModel.MATCHES_PAGE,
            size = PaginationModel.MATCHES_SIZE, sort = PaginationModel.MATCHES_SORT_COLUMN) Pageable pageable) {
        if (match.getResults() == null) {
            return ResponseEntity.ok(matchesService.filterMatches(match.getTournament(),
                    match.getFirstPlayer(), match.getSecondPlayer(), pageable));
        }
        logger.info("Updating {} matches", match.getResults().size());
        Page<Match> matches = matchesService.updateMatches(match.getTournament(), match.getFirstPlayer(),
                match.getSecondPlayer(), pageable, match.getResults());
        return ResponseEntity.ok(matches);
    }

    /**
     * POST request for filtering matches by tournament and players
     *
     * @param searchValues A {@link Match} instance containing filtering data
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     *
     * @return A {@link ResponseEntity} instance with filtered matches and OK HTTP status
     */
    @PostMapping("/filter")
    public ResponseEntity<Page<Match>> filterMatches(@RequestBody Match searchValues,
            @PageableDefault(direction = Sort.Direction.ASC, page = PaginationModel.MATCHES_PAGE,
                    size = PaginationModel.MATCHES_SIZE, sort = PaginationModel.MATCHES_SORT_COLUMN) Pageable pageable) {
        logger.debug("Finding matches");
        Page<Match> foundMatches = matchesService.filterMatches(searchValues.getTournament(),
                searchValues.getFirstPlayer(), searchValues.getSecondPlayer(), pageable);
        logger.info("Successfully retrieved {} matches", foundMatches.getNumberOfElements());
        return ResponseEntity.ok(foundMatches);
    }
}
