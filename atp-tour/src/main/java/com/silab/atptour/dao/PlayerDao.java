package com.silab.atptour.dao;

import com.silab.atptour.entity.Player;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lazar
 */
@Repository
public interface PlayerDao extends JpaRepository<Player, Long>{

    @Query("SELECT p FROM Player p WHERE p.rank>0 ORDER BY p.rank")
    public List<Player> findAllRankedPlayers();
    
}
