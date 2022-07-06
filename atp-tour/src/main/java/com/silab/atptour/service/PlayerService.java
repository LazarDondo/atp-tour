package com.silab.atptour.service;

import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Represents a service containing all the logic for player data management
 * 
 * @author Lazar
 */
public interface PlayerService {

    /**
     * Adds a new player to the database
     * 
     * @param player A {@link Player} object to be added
     * 
     * @return An added {@link Player}
     */
    public Player addPlayer(Player player);

    /**
     * Updates player data in the database
     * 
     * @param player A {@link Player} object to be updated
     * 
     * @return An updated {@link Player}
     * 
     * @throws AtpEntityNotFoundException If the player doesn't exist in the database
     */
    public Player updatePlayer(Player player) throws AtpEntityNotFoundException;

    /**
     * Gets the player with the given id from the database
     * 
     * @param id A long representing player's id
     * 
     * @return A {@link Player} with the given id
     * 
     * @throws AtpEntityNotFoundException If there's no {@link Player} with the given id
     */
    public Player getPlayer(long id) throws AtpEntityNotFoundException;

    /**
     * Gets players from the database. Supports pagination
     * 
     * @param firstName A string representing player's first name
     * @param lastName A string representing player's last name
     * @param birthCountry A string representing name of the player's birth country
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     * 
     * @return A {@link Page} of players
     */
    public Page<Player> getAllPlayers(String firstName, String lastName, String birthCountry, Pageable pageable);
}
