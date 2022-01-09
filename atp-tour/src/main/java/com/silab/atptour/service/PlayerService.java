package com.silab.atptour.service;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.EntityNotFoundException;
import java.util.List;

/**
 *
 * @author Lazar
 */
public interface PlayerService {

    public Player addPlayer(Player player);

    public Player updatePlayer(Player player) throws EntityNotFoundException;

    public Player getPlayer(long id) throws EntityNotFoundException;

    public List<Player> getAllPlayers();
    
    public List<Match> getMatches(long id) throws EntityNotFoundException;
}
