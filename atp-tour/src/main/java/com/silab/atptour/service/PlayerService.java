package com.silab.atptour.service;

import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import java.util.List;

/**
 *
 * @author Lazar
 */
public interface PlayerService {

    public Player addPlayer(Player player);

    public Player updatePlayer(Player player) throws AtpEntityNotFoundException;

    public Player getPlayer(long id) throws AtpEntityNotFoundException;

    public List<Player> getAllPlayers();
}
