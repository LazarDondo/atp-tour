package com.silab.atptour.dao;

import com.silab.atptour.entity.Player;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Represents repository for player data management
 *
 * @author Lazar
 */
@Repository
public interface PlayerDao extends JpaRepository<Player, Long> {

    /**
     * Finds all ranked players ordered ascending by rank
     *
     * @return A {@link List} with ranked players
     */
    @Query("SELECT p FROM Player p WHERE p.rank>0 ORDER BY p.rank")
    public List<Player> findAllRankedPlayers();

    /**
     * Finds all players ordered descending by live points
     * 
     * @return A {@link List} of players
     */
    @Query("SELECT p FROM Player p ORDER BY p.livePoints DESC")
    public List<Player> findAllPlayersOrderedByLivePoints();

}
