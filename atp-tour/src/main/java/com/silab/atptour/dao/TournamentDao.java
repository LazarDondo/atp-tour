package com.silab.atptour.dao;

import com.silab.atptour.entity.Tournament;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents repository for tournament data management
 * 
 * @author Lazar
 */
@Repository
public interface TournamentDao extends JpaRepository<Tournament, Long> {

    /**
     * Finds a tournament by name
     * 
     * @param name A string representing the tournament name
     * 
     * @return An {@link Optional} tournament
     */
    public Optional<Tournament> findTournamentByName(String name);
}
