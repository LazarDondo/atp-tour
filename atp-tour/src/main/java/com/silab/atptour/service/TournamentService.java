package com.silab.atptour.service;

import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.EntityNotFoundException;
import java.util.List;

/**
 *
 * @author Lazar
 */
public interface TournamentService {

    public Tournament addTournament(Tournament tournament);

    public Tournament updateTournament(Tournament tournament) throws EntityNotFoundException;

    public Tournament getTournament(long id) throws EntityNotFoundException;
    
    public List<Tournament> getAllTournaments();
    
    public List<Match> getMatches(long id) throws EntityNotFoundException;

    public void deleteTournament(long id) throws EntityNotFoundException;
}
