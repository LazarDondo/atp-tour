package com.silab.atptour.jobs;

import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Player;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lazar
 */
@Component
public class AtpRankingScheduler {
    
    @Autowired
    private PlayerDao playerDao;
    
    private final Logger logger = LoggerFactory.getLogger(AtpRankingScheduler.class);
    
    @Scheduled(cron = "${scheduling.rank.cron}")
    public void rankPlayers(){
        logger.info("Ranking ATP list players");
        int rank=1;
        List<Player> players = playerDao.findAllPlayersOrderedByLivePoints();
        for (Player player : players) {
            player.setRank(rank++);
            player.setCurrentPoints(player.getLivePoints());
        }
        playerDao.saveAll(players);
    }
    
    
}
