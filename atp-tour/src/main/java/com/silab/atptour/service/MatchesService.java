package com.silab.atptour.service;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import java.util.List;

/**
 *
 * @author Lazar
 */
public interface MatchesService {

    public List<Match> addMatches(List<Match> matches);

    public List<Match> updateMatches(List<Match> matches);

    public List<Match> filterMatches(Tournament tournament, Player firstPlayer, Player secondPlayer);
}
