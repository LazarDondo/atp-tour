package com.silab.atptour.controller;

import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.PlayerService;
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
@RequestMapping("player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    private final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        logger.info("Adding new player {} {}", player.getFirstName(), player.getLastName());
        Player addedPlayer = playerService.addPlayer(player);
        logger.info("Successfully added new player");
        return ResponseEntity.ok(addedPlayer);
    }

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

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        List<Player> players = playerService.getAllPlayers();
        logger.info("Successfully retrieved {} players", players.size());
        return ResponseEntity.ok(players);
    }
}
