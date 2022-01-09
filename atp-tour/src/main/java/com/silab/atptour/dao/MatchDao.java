package com.silab.atptour.dao;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.id.MatchId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lazar
 */
@Repository
public interface MatchDao extends JpaRepository<Match, MatchId>{
    
    public List<Match> findByFirstPlayerAndSecondPlayer(Player firstPlayer, Player secondPlayer);
}
