package com.silab.atptour.service;

import com.silab.atptour.entity.Match;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import java.util.List;

/**
 *
 * @author Lazar
 */
public interface MatchesService {
    
    public List<Match> addMatches(List<Match> matches);
    
    public List<Match> updateMatches(List<Match> matches);
    
    public List<Match> getH2HMatches(long firstPlayerId, long secondPlayerId) throws AtpEntityNotFoundException;
}
