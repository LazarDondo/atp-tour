package com.silab.atptour.jobs;

import com.silab.atptour.dao.IncomeDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Income;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler for updating points and rankings on the ATP list
 * 
 * @author Lazar
 */
@Component
public class AtpRankingScheduler {
    
    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private PlayerDao playerDao;
    
    @Autowired
    private IncomeDao incomeDao;

    private final Logger logger = LoggerFactory.getLogger(AtpRankingScheduler.class);

    /**
     * Equalizes live and current points after which ranks all players based on the current points. Job is triggered every Monday at midnight.
     */
    @Scheduled(cron = "${scheduling.rank.cron}")
    public void rankPlayers() {
        logger.info("Job for ranking players on the ATP list");
        int rank = 1;
        List<Player> players = playerDao.findAllPlayersOrderedByLivePoints();
        for (Player player : players) {
            player.setRank(rank++);
            player.setCurrentPoints(player.getLivePoints());
        }
        playerDao.saveAll(players);
    }
    
    /**
     * Check whether the tournaments that start today were held last year and if so deducts points that players have won in previous year.
     * Job is triggered every day at midnight
     */
    @Scheduled(cron = "${scheduling.tournament.cron}")
    public void deductPointsFromPreviousTournaments() {
        logger.info("Job for deducing points from previous tournaments");
        List<Tournament> tournaments = tournamentDao.findTournamentByStartDate(LocalDate.now());
        logger.debug("Found {} tournaments starting today", tournaments.size());
        for (Tournament tournament : tournaments) {
            deductPointsFromPreviousTournament(tournament);
        }
    }
    
    /**
     * Deducts points for the players who participated on the on the same tournament last year
     * 
     * @param tournament A {@link Tournament} object for which points from the previous year should be deducted 
     */
    private void deductPointsFromPreviousTournament(Tournament tournament) {
        logger.debug("Subtracting points for players who participated in previous year on {}", tournament.getName());
        String previousTournamentName = tournament.getName().split("-")[0] + "-" + (tournament.getStartDate().getYear() - 1);
        Optional<Tournament> previousTournament = tournamentDao.findTournamentByName(previousTournamentName);
        
        if (previousTournament.isEmpty()) {
            logger.debug("{} wasn't been held last year", previousTournamentName);
            return;
        }
        
        List<Income> playerIncomes = incomeDao.findIncomesByTournament(previousTournament.get());
        for (Income playerIncome : playerIncomes) {
            Player player = playerDao.findById(playerIncome.getPlayer().getId()).get();
            player.setLivePoints(player.getLivePoints() - playerIncome.getPoints());
            playerDao.save(player);
        }
    }
    
    

}
