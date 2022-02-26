package com.silab.atptour.controller;

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
 * Rest controller for tournament data management
 * 
 * @author Lazar
 */
@RestController
@RequestMapping("tournament")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    private final Logger logger = LoggerFactory.getLogger(TournamentController.class);

    /**
     * POST request for adding new tournament to the database
     *
     * @param tournament A {@link Tournament} object to be added to the database
     *
     * @return
     *      <ul>
     *          <li>A {@link ResponseEntity} instance with the added tournament and OK HTTP status if the tournament has been created successfully</li>
     *          <li>A {@link ResponseEntity} instance with error message and CONFLICT HTTP status if a tournament with the same name already exists</li>
     *      </ul>       
     */
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

    /**
     * PUT request for updating tournament's data
     *
     * @param tournament A {@link Tournament} object to be updated
     *
     * @return
     *      <ul>
     *          <li>A {@link ResponseEntity} instance with the updated tournament and OK HTTP status if the tournament has been updated successfully</li>
     *          <li>A {@link ResponseEntity} instance with error message and NOT_FOUND HTTP status if the tournament doesn't exist</li>
     *          <li>A {@link ResponseEntity} instance with error message and CONFLICT HTTP status if a tournament with the same name already exists</li>
     *      </ul>       
     */
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

    /**
     * GET request for finding a tournament with the given id in the database
     *
     * @param id A long representing tournament's id
     *
     * @return
     *      <ul>
     *          <li>A {@link ResponseEntity} instance with the found tournament and OK HTTP status if the tournament has been found</li>
     *          <li>A {@link ResponseEntity} instance with error message and NOT_FOUND HTTP status if there's no tournament with the given id</li>
     *      </ul>
     */
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

    /**
     * GET request for retrieving all tournaments from the database
     * 
     * @return A {@link ResponseEntity} instance with found tournaments and OK HTTP status 
     */
    @GetMapping
    public ResponseEntity<List<Tournament>> getTournaments() {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        logger.info("Successfully retrieved {} tournaments", tournaments.size());
        return ResponseEntity.ok(tournaments);
    }

    /**
     * DELETE request for removing a tournament from the database
     * 
     * @param id A long representing tournament's id
     * 
     * @return 
     *      <ul>
     *          <li>A {@link ResponseEntity} instance with success message and OK HTTP status</li>
     *          <li>A {@link ResponseEntity} instance with error message and NOT_FOUND HTTP status if there's no tournament with the given id</li>
     *      </ul>
     */
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
