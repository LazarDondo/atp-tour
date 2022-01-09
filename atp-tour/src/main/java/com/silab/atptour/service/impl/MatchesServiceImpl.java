package com.silab.atptour.service.impl;

import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.exceptions.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.silab.atptour.service.MatchesService;

/**
 *
 * @author Lazar
 */
@Service
public class MatchesServiceImpl implements MatchesService {

    @Autowired
    MatchDao matchDao;

    @Autowired
    PlayerDao playerDao;

    private final Logger logger = LoggerFactory.getLogger(MatchesServiceImpl.class);

    @Override
    public List<Match> addMatches(List<Match> matches) {
        logger.debug("Adding {} matches", matches.size());
        List<Match> savedMatches = new ArrayList<>();
        for (Match match : matches) {
            savedMatches.add(matchDao.save(match));
        }
        return savedMatches;
    }

    @Override
    public List<Match> updateMatches(List<Match> matches) {
        logger.debug("Updating {} matches", matches.size());
        List<Match> updatedMatches = new ArrayList<>();
        for (Match match : matches) {
            updatedMatches.add(matchDao.save(match));
        }
        return updatedMatches;
    }

    @Override
    public List<Match> getH2HMatches(long firstPlayerId, long secondPlayerId) throws EntityNotFoundException {
        logger.debug("Finding matches for players with id {} and {}", firstPlayerId, secondPlayerId);
        Optional<Player> optionalFirstPlayer = playerDao.findById(firstPlayerId);
        if (optionalFirstPlayer.isEmpty()) {
            logger.error("Player with id {} doesn't exist", firstPlayerId);
            throw new EntityNotFoundException("Player with id " + firstPlayerId + " doesn't exist");
        }

        Optional<Player> optionalSecondPlayer = playerDao.findById(secondPlayerId);
        if (optionalSecondPlayer.isEmpty()) {
            logger.error("Player with id {} doesn't exist", secondPlayerId);
            throw new EntityNotFoundException("Player with id " + secondPlayerId + " doesn't exist");
        }
        return matchDao.findByFirstPlayerAndSecondPlayer(optionalFirstPlayer.get(), optionalSecondPlayer.get());
    }

}
