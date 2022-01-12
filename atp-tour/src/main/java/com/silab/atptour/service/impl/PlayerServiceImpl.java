package com.silab.atptour.service.impl;

import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.EntityNotFoundException;
import com.silab.atptour.service.PlayerService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lazar
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerDao playerDao;

    private final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);

    @Override
    public Player addPlayer(Player player) {
        logger.debug("Adding player {} {}", player.getFirstName(), player.getLastName());
        return playerDao.save(player);
    }

    @Override
    public Player updatePlayer(Player player) throws EntityNotFoundException {
        logger.debug("Finding player {} {}", player.getFirstName(), player.getLastName());
        Optional<Player> optionalPlayer = playerDao.findById(player.getId());
        if (optionalPlayer.isEmpty()) {
            logger.error("Player {} {} doesn't exist", player.getFirstName(), player.getLastName());
            throw new EntityNotFoundException("Player " + player.getFirstName() + " " + player.getLastName() + " doesn't exist");
        }
        return playerDao.save(player);
    }

    @Override
    public Player getPlayer(long id) throws EntityNotFoundException {
        logger.debug("Finding player with id {}", id);
        Optional<Player> optionalPlayer = playerDao.findById(id);
        if (optionalPlayer.isEmpty()) {
            logger.error("Player with id {} doesn't exist", id);
            throw new EntityNotFoundException("Player with id " + id + " doesn't exist");
        }
        return optionalPlayer.get();
    }

    @Override
    public List<Player> getAllPlayers() {
        logger.debug("Finding all tennis players");
        return playerDao.findAll();
    }

    @Override
    public List<Match> getMatches(long id) throws EntityNotFoundException {
        logger.debug("Finding player with id {}", id);
        Optional<Player> optionalPlayer = playerDao.findById(id);
        if (optionalPlayer.isEmpty()) {
            logger.error("Player with id {} doesn't exist", id);
            throw new EntityNotFoundException("Player with id " + id + " doesn't exist");
        }
        return optionalPlayer.get().getMatches();
    }

}