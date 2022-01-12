package com.silab.atptour.controller;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.TournamentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("tournament")
public class TournamentController {

    private final Logger logger = LoggerFactory.getLogger(TournamentController.class);

    @Autowired
    TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Tournament> addTournament(@RequestBody Tournament tournament) {
        logger.info("Adding new tournament {} for year {}", tournament.getName(), tournament.getStartDate());
        try {
            Tournament addedTournament = tournamentService.addTournament(tournament);
            logger.info("Successfully added new tournament");
            return ResponseEntity.ok(addedTournament);
        } catch (AtpEntityExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<Tournament> updateTournament(@RequestBody Tournament tournament) {
        logger.info("Updating tournament with id {}", tournament.getId());
        try {
            Tournament updatedTournament = tournamentService.updateTournament(tournament);
            logger.info("Successfully updated tournament {}", updatedTournament.getName());
            return ResponseEntity.ok(updatedTournament);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (AtpEntityExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable long id) {
        logger.info("Finding tournament with id {}", id);
        try {
            Tournament foundTournament = tournamentService.getTournament(id);
            logger.info("Successfully retrieved tournament {}", foundTournament.getName());
            return ResponseEntity.ok(foundTournament);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getTournaments() {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        logger.info("Successfully retrieved {} tournaments", tournaments.size());
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("{id}/matches")
    public ResponseEntity<List<Match>> getMatches(@PathVariable long id) {
        logger.info("Finding matches from tournament with id {}", id);
        try {
            List<Match> matches = tournamentService.getMatches(id);
            logger.info("Successfully retrieved {} tournament matches", matches.size());
            return ResponseEntity.ok(matches);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTournament(@PathVariable long id) {
        logger.debug("Deleting tournament with id {}", id);
        try {
            tournamentService.deleteTournament(id);
            logger.info("Successfully deleted tournament with id " + id);
            return ResponseEntity.ok("Tournament deleted successfully");
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
