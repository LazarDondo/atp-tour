package com.silab.atptour.controller;

import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.model.PaginationModel;
import com.silab.atptour.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for player data management
 *
 * @author Lazar
 */
@RestController
@RequestMapping("player")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    private final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    /**
     * POST request for adding new player to the database
     *
     * @param player A {@link Player} object to be added to the database
     *
     * @return A {@link ResponseEntity} instance with the added player and OK HTTP status
     */
    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        logger.info("Adding new player {} {}", player.getFirstName(), player.getLastName());
        Player addedPlayer = playerService.addPlayer(player);
        logger.info("Successfully added new player");
        return ResponseEntity.ok(addedPlayer);
    }

    /**
     * PUT request for updating player's data
     *
     * @param player A {@link Player} object to be updated
     *
     * @return
     *      <ul>
     *          <li>A {@link ResponseEntity} instance with the updated player and OK HTTP status if player has been updated successfully</li>
     *          <li>A {@link ResponseEntity} instance with error message and NOT_FOUND HTTP status if player doesn't exist</li>
     *      </ul>
     */
    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        logger.info("Updating player {} {}", player.getFirstName(), player.getLastName());
        try {
            Player updatedPlayer = playerService.updatePlayer(player);
            logger.info("Successfully updated player");
            return ResponseEntity.ok(updatedPlayer);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET request for finding a player with the given id in the database
     *
     * @param id A long representing player's id
     *
     * @return
     *      <ul>
     *          <li>A {@link ResponseEntity} instance with the found player and OK HTTP status if the player has been found</li>
     *          <li>A {@link ResponseEntity} instance with error message and NOT_FOUND HTTP status if there's no player with the given id</li>
     *      </ul>
     */
    @GetMapping("{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") long id) {
        logger.debug("Finding player with id {}", id);
        try {
            Player player = playerService.getPlayer(id);
            logger.info("Succesfully retrieved player {} {}", player.getFirstName(), player.getLastName());
            return ResponseEntity.ok(player);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET request for retrieving ranked players from the database
     * 
     * @param firstName A string representing player's first name
     * @param lastName A string representing player's last name
     * @param birthCountry A string representing name of player's birth country
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     * 
     * @return A {@link ResponseEntity} instance with found players and OK HTTP status
     */
    @GetMapping
    public ResponseEntity<Page<Player>> getPlayers( @RequestParam(required = false, defaultValue = "") String firstName,
           @RequestParam(required = false, defaultValue = "") String lastName,
           @RequestParam(required = false) String birthCountry,
           @PageableDefault(direction = Sort.Direction.ASC, page = PaginationModel.PLAYER_PAGE,
                   size = PaginationModel.PLAYER_SIZE, sort = PaginationModel.PLAYER_SORT_COLUMN) Pageable pageable){
        Page<Player> players = playerService.getAllPlayers(firstName, lastName, birthCountry, pageable);
        logger.info("Successfully retrieved {} players", players.getNumberOfElements());
        return ResponseEntity.ok(players);
    }
}
