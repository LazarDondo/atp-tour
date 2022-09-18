package com.silab.atptour.service;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Represents a service containing all the logic for matches data management
 *
 * @author Lazar
 */
public interface MatchesService {

    /**
     * Saves new and updates existing matches in the database
     *
     * @param tournament A {@link Tournament} object in which matches were played
     * @param firstPlayer A {@link Player} object who participated in the matches
     * @param secondPlayer A {@link Player} object who participated in the matches
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     * @param results A {@link List} of matches to be saved
     *
     * @return A {@link Page} of filtered matches
     */
    public Page<Match> updateMatches(Tournament tournament, Player firstPlayer, Player secondPlayer,
            Pageable pageable, List<Match> results);
    
    /**
     * Filters matches based on the tournament and players
     *
     * @param tournament A {@link Tournament} object in which matches were played
     * @param firstPlayer A {@link Player} object who participated in the matches
     * @param secondPlayer A {@link Player} object who participated in the matches
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     *
     * @return A {@link Page} of filtered matches
     */
    public Page<Match> filterMatches(Tournament tournament, Player firstPlayer, Player secondPlayer, Pageable pageable);
}
