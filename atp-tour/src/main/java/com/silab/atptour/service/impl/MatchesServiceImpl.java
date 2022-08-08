package com.silab.atptour.service.impl;

import com.silab.atptour.dao.IncomeDao;
import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Income;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.entity.id.IncomeId;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.model.AtpModel;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.silab.atptour.service.MatchesService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Represent an implementation of the {@link MatchesService} interface
 * 
 * @author Lazar
 */
@Service
public class MatchesServiceImpl implements MatchesService {

    @Autowired
    PlayerDao playerDao;
    
    @Autowired
    private MatchDao matchDao;
    
    @Autowired
    private IncomeDao incomeDao;

    private final Logger logger = LoggerFactory.getLogger(MatchesServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> updateMatches(List<Match> matches) {
        logger.debug("Updating {} matches", matches.size());
        for (Match match : matches) {
            if(match.getWinner()!=null){
                increasePlayerPoints(match);
            }
        }
        matchDao.saveAll(matches);
        return matches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Match> filterMatches(Tournament tournament, Player firstPlayer, Player secondPlayer, Pageable pageable) {
        logger.info("Filtering matches by tournament: {}, first player: {}, second player: {}", tournament, firstPlayer, secondPlayer);
        return matchDao.filterMatches(tournament, firstPlayer, secondPlayer, pageable);
    }

    /**
     * Increases the live points and {@link Income} from the tournament for the match winner
     * 
     * @param match A {@link Match} for which the winner's live points will be increased
     */
    private void increasePlayerPoints(Match match) {
        int roundPoints = getRoundPoints(match.getRound());
        Player winner = match.getWinner();
        winner.setLivePoints(winner.getLivePoints()+roundPoints);
        Income income = incomeDao.findById(new IncomeId(match.getTournament().getId(), match.getWinner().getId())).get();
        income.setPoints(income.getPoints()+roundPoints);
        incomeDao.save(income);
        playerDao.save(winner);
    }
    
    /**
     * Gets points based on the round
     * 
     * @param round A string representing the round of the {@link Match}
     * 
     * @return An int representing points for the given {@link Match} round
     */
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
