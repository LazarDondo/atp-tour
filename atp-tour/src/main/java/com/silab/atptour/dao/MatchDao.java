package com.silab.atptour.dao;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.entity.id.MatchId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Represents repository for matches data management
 *
 * @author Lazar
 */
@Repository
public interface MatchDao extends JpaRepository<Match, MatchId> {

    /**
     * Filters matches by tournament and players
     *
     * @param tournament A {@link Tournament} in which the matches were played
     * @param firstPlayer A {@link Player} who has participated in the matches
     * @param secondPlayer A {@link Player} who has participated in the matches
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     * 
     * @return A {@link Page} with filtered matches
     */
    @Query("SELECT m FROM Match m WHERE (:tournament is null OR m.tournament = :tournament)"
            + "AND (:firstPlayer is null or m.firstPlayer = :firstPlayer OR m.secondPlayer = :firstPlayer)"
            + " AND (:secondPlayer is null or m.secondPlayer = :secondPlayer OR m.firstPlayer = :secondPlayer)"
            + "ORDER BY m.matchDate")
    public Page<Match> filterMatches(@Param("tournament") Tournament tournament,
            @Param("firstPlayer") Player firstPlayer, @Param("secondPlayer") Player secondPlayer, Pageable pageable);
}
