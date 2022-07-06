package com.silab.atptour.dao;

import com.silab.atptour.entity.Player;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * Finds ranked players
     *
     * @param firstName A string representing player's first name
     * @param lastName A string representing player's last name
     * @param birthCountry A string representing name of player's birth country
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     * 
     * @return A {@link Page} of ranked players
     */
    @Query("SELECT p FROM Player p WHERE  p.rank>0 AND p.firstName LIKE %:firstName%"
            + " AND p.lastName LIKE %:lastName% AND (:birthCountry IS NULL OR p.birthCountry.name = :birthCountry)")
    public Page<Player> findAllRankedPlayers(String firstName,
             String lastName, String birthCountry, Pageable pageable);

    /**
     * Finds all players ordered descending by live points
     * 
     * @return A {@link List} of players
     */
    @Query("SELECT p FROM Player p ORDER BY p.livePoints DESC")
    public List<Player> findAllPlayersOrderedByLivePoints();

}
