package com.silab.atptour.dao;

import com.silab.atptour.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lazar
 */
@Repository
public interface PlayerDao extends JpaRepository<Player, Long>{
    
}
