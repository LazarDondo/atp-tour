package com.silab.atptour.dao;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.entity.id.MatchId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lazar
 */
@Repository
public interface MatchDao extends JpaRepository<Match, MatchId> {

    @Query("SELECT m FROM Match m WHERE (:tournament is null or m.tournament = :tournament)"
            + "and (:firstPlayer is null or m.firstPlayer = :firstPlayer or m.secondPlayer = :firstPlayer)"
            + " and (:secondPlayer is null or m.secondPlayer = :secondPlayer or m.firstPlayer = :secondPlayer)")
    public List<Match> filterMatches(@Param("tournament") Tournament tournament,
            @Param("firstPlayer") Player firstPlayer, @Param("secondPlayer") Player secondPlayer);
}
