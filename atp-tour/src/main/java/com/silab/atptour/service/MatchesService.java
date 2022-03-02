package com.silab.atptour.service;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import java.util.List;

/**
 * Represents a service containing all the logic for matches data management
 *
 * @author Lazar
 */
public interface MatchesService {

    /**
     * Saves new and updates existing matches in the database
     *
     * @param matches A {@link List} of matches to be saved
     *
     * @return A {@link List} of all matches from the tournament from which were the forwarded matches
     */
    public List<Match> updateMatches(List<Match> matches);

    /**
     * Filters matches based on the tournament and players
     *
     * @param tournament A {@link Tournament} object in which matches were played
     * @param firstPlayer A {@link Player} object who participated in the matches
     * @param secondPlayer A {@link Player} object who participated in the matches
     *
     * @return A {@link List} of filtered matches
     */
    public List<Match> filterMatches(Tournament tournament, Player firstPlayer, Player secondPlayer);
}
