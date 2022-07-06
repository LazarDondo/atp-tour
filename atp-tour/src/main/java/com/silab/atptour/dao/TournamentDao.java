package com.silab.atptour.dao;

import com.silab.atptour.entity.Tournament;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    
    /**
     * Finds tournaments
     * 
     * @param name A string representing tournament's name
     * @param hostCountry A string representing host country
     * @param tournamentType A string representing tournament type
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     * 
     * @return A {@link Page} of tournaments
     */
    @Query("SELECT t FROM Tournament t WHERE t.name LIKE %:name% AND (:hostCountry IS NULL OR t.hostCountry.name = :hostCountry)"
            + " AND (:tournamentType IS NULL OR t.tournamentType = :tournamentType)")
    public Page<Tournament> findAllTournaments(String name, String hostCountry, String tournamentType, Pageable pageable);
    
    /**
     * Finds tournaments by start date
     * 
     * @param startDate A {@link LocalDate} instance representing tournament's start date
     * 
     * @return A {@link List} of tournaments
     */
    public List<Tournament> findTournamentByStartDate(LocalDate startDate);
}
