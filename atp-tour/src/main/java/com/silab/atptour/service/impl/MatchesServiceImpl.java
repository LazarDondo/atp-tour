package com.silab.atptour.service.impl;

import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import java.util.ArrayList;
import java.util.List;
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
    public List<Match> filterMatches(Tournament tournament, Player firstPlayer, Player secondPlayer) {
        logger.info("Filtering matches by tournament: {}, first player: {}, second player: {}", tournament, firstPlayer, secondPlayer);
        return matchDao.filterMatches(tournament, firstPlayer, secondPlayer);
    }

}
