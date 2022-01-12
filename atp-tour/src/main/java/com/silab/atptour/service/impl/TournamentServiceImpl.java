package com.silab.atptour.service.impl;

import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.TournamentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lazar
 */
@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    TournamentDao tournamentDao;

    private final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);

    @Override
    public Tournament addTournament(Tournament tournament) throws AtpEntityExistsException {
        String name = tournament.getName()+"-"+tournament.getStartDate().getYear();
        tournament.setName(name);
        if(tournamentDao.findTournamentByName(name).isPresent()){
            throw new AtpEntityExistsException("Tournament with name "+name+" already exists");
        }
        logger.debug("Adding new {} tournament", tournament.getName());
        return tournamentDao.save(tournament);
    }

    @Override
    public Tournament updateTournament(Tournament tournament) throws AtpEntityNotFoundException, AtpEntityExistsException {
        logger.debug("Finding tournament {}", tournament.getName());
        String name = tournament.getName()+"-"+tournament.getStartDate().getYear();
        Optional<Tournament> optionalTournament = tournamentDao.findById(tournament.getId());
        if (optionalTournament.isEmpty()) {
            throw new AtpEntityNotFoundException("Tournament " + tournament.getName() + " doesn't exist");
        }
        if(!optionalTournament.get().getName().equals(name) && tournamentDao.findTournamentByName(name).isPresent()){
            throw new AtpEntityExistsException("Tournament with name "+name+" already exists");
        }
        tournament.setName(name);
        logger.debug("Updating tournament {}", tournament.getName());
        return tournamentDao.save(tournament);
    }

    @Override
    public Tournament getTournament(long id) throws AtpEntityNotFoundException {
        Optional<Tournament> optionalTournament = tournamentDao.findById(id);
        if (optionalTournament.isEmpty()) {
            throw new AtpEntityNotFoundException("Tournament doesn't exist");
        }
        return optionalTournament.get();
    }

    @Override
    public List<Tournament> getAllTournaments() {
        logger.debug("Retreiving all tournaments");
        return tournamentDao.findAll();
    }

    @Override
    public List<Match> getMatches(long id) throws AtpEntityNotFoundException {
        Optional<Tournament> optionalTournament = tournamentDao.findById(id);
        if (optionalTournament.isEmpty()) {
            throw new AtpEntityNotFoundException("Tournament doesn't exist");
        }
        return optionalTournament.get().getMatches();
    }

    @Override
    public void deleteTournament(long id) throws AtpEntityNotFoundException {
        logger.debug("Deleting tournament with id {}", id);
        if (tournamentDao.findById(id).isEmpty()) {
            throw new AtpEntityNotFoundException("Tournament doesn't exist");
        }
        tournamentDao.deleteById(id);
    }

}
