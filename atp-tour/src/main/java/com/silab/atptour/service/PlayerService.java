package com.silab.atptour.service;

import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import java.util.List;

/**
 * Represents a service containing all the logic for managing player data
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
     * Gets all players from the database
     * 
     * @return A {@link List} of players
     */
    public List<Player> getAllPlayers();
}
