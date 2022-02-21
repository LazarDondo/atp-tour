package com.silab.atptour.service.impl;

import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.model.AtpModel;
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
        for (Match match : matches) {
            if(match.getWinner()!=null){
                increasePlayerPoints(match);
            }
        }
        matchDao.saveAll(matches);
        return matchDao.filterMatches(matches.get(0).getTournament(), null, null);
    }

    @Override
    public List<Match> filterMatches(Tournament tournament, Player firstPlayer, Player secondPlayer) {
        logger.info("Filtering matches by tournament: {}, first player: {}, second player: {}", tournament, firstPlayer, secondPlayer);
        return matchDao.filterMatches(tournament, firstPlayer, secondPlayer);
    }

    private void increasePlayerPoints(Match match) {
        int roundPoints = getRoundPoints(match.getRound());
        Player winner = match.getWinner();
        winner.setLivePoints(winner.getLivePoints()+roundPoints);
        playerDao.save(winner);
    }
    
    private int getRoundPoints(String round){
        switch(round){
            case AtpModel.GRAND_SLAM_EIGHTS_FINALS:
                return AtpModel.GRAND_SLAM_EIGHTS_FINALS_POINTS;
            case AtpModel.GRAND_SLAM_QUATER_FINALS:
                return AtpModel.GRAND_SLAM_QUATER_FINALS_POINTS;
            case AtpModel.GRAND_SLAM_SEMI_FINALS:
                return AtpModel.GRAND_SLAM_SEMI_FINALS_POINTS;
            case AtpModel.GRAND_SLAM_FINALS:
                return AtpModel.GRAND_SLAM_FINALS_POINTS;
            default:
                return 0;
        }
    }

}
