package com.silab.atptour.service.impl;

import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.PlayerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represent an implementation of the {@link PlayerService} interface
 * 
 * @author Lazar
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDao playerDao;

    private final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Player addPlayer(Player player) {
        logger.debug("Adding player {} {}", player.getFirstName(), player.getLastName());
        player.setLivePoints(player.getCurrentPoints());
        return playerDao.save(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player updatePlayer(Player player) throws AtpEntityNotFoundException {
        logger.debug("Finding player {} {}", player.getFirstName(), player.getLastName());
        Optional<Player> optionalPlayer = playerDao.findById(player.getId());
        if (optionalPlayer.isEmpty()) {
            throw new AtpEntityNotFoundException("Player " + player.getFirstName() + " " + player.getLastName() + " doesn't exist");
        }
        return playerDao.save(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer(long id) throws AtpEntityNotFoundException {
        logger.debug("Finding player with id {}", id);
        Optional<Player> optionalPlayer = playerDao.findById(id);
        if (optionalPlayer.isEmpty()) {
            throw new AtpEntityNotFoundException("Player with id " + id + " doesn't exist");
        }
        return optionalPlayer.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Player> getAllPlayers(String firstName, String lastName, String birthCountry, Pageable pageable) {
        logger.debug("Finding all tennis players");
        return playerDao.findAllRankedPlayers(firstName, lastName, birthCountry, pageable);
    }
}
