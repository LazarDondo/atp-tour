package com.silab.atptour.service.impl;

import com.silab.atptour.dao.IncomeDao;
import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Income;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.entity.id.IncomeId;
import com.silab.atptour.exceptions.AtpException;
import com.silab.atptour.model.AtpModel;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.silab.atptour.service.MatchesService;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represent an implementation of the {@link MatchesService} interface
 *
 * @author Lazar
 */
@Service
@Transactional
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
    public Page<Match> updateMatches(Tournament tournament, Player firstPlayer, Player secondPlayer,
            Pageable pageable, List<Match> results) {
        logger.debug("Updating {} matches", results.size());
        for (Match match : results) {
            if (match.getResult() != null || !match.getResult().isEmpty()) {
                decideWinner(match);
                prepareNextMatch(match);
                increasePlayerPoints(match);
            }
        }
        matchDao.saveAll(results);
        return matchDao.filterMatches(tournament, firstPlayer, secondPlayer, pageable);
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
     * Decides match winner
     *
     * @param match
     */
    private void decideWinner(Match match) {
        String result = match.getResult();
        if (AtpModel.GRAND_SLAM.equals(match.getTournament().getTournamentType())) {
            if (result.equals("3:0") || result.equals("3:1") || result.equals("3:2")) {
                match.setWinner(match.getFirstPlayer());
                return;
            }
        }
        if (result.equals("2:0") || result.equals("2:1") || result.equals("1:0") || result.equals("retired")) {
            match.setWinner(match.getFirstPlayer());
        } else {
            match.setWinner(match.getSecondPlayer());
        }
    }

/**
 * Prepares next match by either creating one if it doesn't exist or updating the existing one.
 *
 * @param match Previous round match
 */
private void prepareNextMatch(Match match){

    Optional<Match> optionalMatch = matchDao.findById(match.getId());
    if (optionalMatch.isEmpty()) {
        throw new AtpException("Match doesn't exist");
    }
    Match nextMatch = optionalMatch.get().getNextMatch();
    match.setNextMatch(nextMatch);

    if (nextMatch != null) {
        if (nextMatch.getFirstPlayer() == null) {
            nextMatch.setFirstPlayer(match.getWinner());
        } else {
            nextMatch.setSecondPlayer(match.getWinner());
        }
            matchDao.save(nextMatch);
    }
    }

    /**
     * Gets next round match date
     *
     * @param match Previous round match
     *
     * @return Next round match date
     */
    private LocalDate getNextRoundMatchDate(Match match){
        return AtpModel.GRAND_SLAM_SEMI_FINALS.equals(match.getMatchDate())?match.getTournament().getCompletionDate():match.getMatchDate().plusDays(1);
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
