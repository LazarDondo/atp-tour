package com.silab.atptour.service;

import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import java.util.List;

/**
 *
 * @author Lazar
 */
public interface TournamentService {

    public Tournament addTournament(Tournament tournament) throws AtpEntityExistsException;

    public Tournament updateTournament(Tournament tournament) throws AtpEntityNotFoundException, AtpEntityExistsException;

    public Tournament getTournament(long id) throws AtpEntityNotFoundException;

    public List<Tournament> getAllTournaments();

    public void deleteTournament(long id) throws AtpEntityNotFoundException;
}
