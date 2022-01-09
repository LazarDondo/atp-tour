package com.silab.atptour.dao;

import com.silab.atptour.entity.Tournament;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lazar
 */
@Repository
public interface TournamentDao extends JpaRepository<Tournament, Long>{
    
    public Optional<Tournament> findTournamentByName(String name);
}
