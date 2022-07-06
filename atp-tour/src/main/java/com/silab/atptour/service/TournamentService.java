package com.silab.atptour.service;

import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Represents a service containing all the logic for tournament data management
 * 
 * @author Lazar
 */
public interface TournamentService {

    /**
     * Adds a new tournament to the database
     * 
     * @param tournament A {@link Tournament} object to be added
     * 
     * @return An added {@link Tournament}
     * 
     * @throws AtpEntityExistsException If a {@link Tournament} with the same name already exists in the database
     */
    public Tournament addTournament(Tournament tournament) throws AtpEntityExistsException;

    /**
     * Updates tournament's data in the database
     * 
     * @param tournament A {@link Tournament} object to be updated
     * 
     * @return An updated {@link Tournament}
     * 
     * @throws AtpEntityNotFoundException If the given {@link Tournament} doesn't exist in the database
     * @throws AtpEntityExistsException If a {@link Tournament} with the same name already exists in the database
     */
    public Tournament updateTournament(Tournament tournament) throws AtpEntityNotFoundException, AtpEntityExistsException;

    /**
     * Gets the tournament with the given id from the database
     * 
     * @param id A long representing tournament's id
     * 
     * @return A {@link Tournament} with the given id
     * 
     * @throws AtpEntityNotFoundException If there's no {@link Tournament} with the given id
     */
    public Tournament getTournament(long id) throws AtpEntityNotFoundException;

    /**
     * Gets all tournaments from the database
     * 
     * @param name A string representing tournament's name
     * @param hostCountry A string representing host country
     * @param tournamentType A string representing tournament type
     * @param pageable An instance of {@link  Pageable} interface used for pagination
     * 
     * @return A {@link Page} of tournaments
     */
    public Page<Tournament> getAllTournaments(String name, String hostCountry, String tournamentType, Pageable pageable);

    /**
     * Deletes the tournament with the given id from the database
     * 
     * @param id A long representing tournament's id
     * 
     * @throws AtpEntityNotFoundException If there's no {@link Tournament} with the given id
     */
    public void deleteTournament(long id) throws AtpEntityNotFoundException;
}
