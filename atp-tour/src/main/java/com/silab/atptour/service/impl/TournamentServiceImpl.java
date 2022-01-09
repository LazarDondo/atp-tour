package com.silab.atptour.service.impl;

import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.EntityNotFoundException;
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
    public Tournament addTournament(Tournament tournament) {
        tournament.setName(tournament.getName()+"-"+tournament.getStartDate().getYear());
        logger.debug("Adding new {} tournament", tournament.getName());
        return tournamentDao.save(tournament);
    }

    @Override
    public Tournament updateTournament(Tournament tournament) throws EntityNotFoundException {
        logger.debug("Finding tournament {}", tournament.getName());
        Optional<Tournament> optionalTournament = tournamentDao.findById(tournament.getId());
        if (optionalTournament.isEmpty()) {
            logger.error("Tournament {} doesn't exist", tournament.getName());
            throw new EntityNotFoundException("Tournament " + tournament.getName() + " doesn't exist");
        }
        logger.debug("Updating tournament {}", tournament.getName());
        return tournamentDao.save(tournament);
    }

    @Override
    public Tournament getTournament(long id) throws EntityNotFoundException {
        Optional<Tournament> optionalTournament = tournamentDao.findById(id);
        if (optionalTournament.isEmpty()) {
            logger.error("Tournament with {} doesn't exist", id);
            throw new EntityNotFoundException("Tournament doesn't exist");
        }
        return optionalTournament.get();
    }

    @Override
    public List<Tournament> getAllTournaments() {
        logger.debug("Retreiving all tournaments");
        return tournamentDao.findAll();
    }

    @Override
    public List<Match> getMatches(long id) throws EntityNotFoundException {
        Optional<Tournament> optionalTournament = tournamentDao.findById(id);
        if (optionalTournament.isEmpty()) {
            logger.error("Tournament with {} doesn't exist", id);
            throw new EntityNotFoundException("Tournament doesn't exist");
        }
        return optionalTournament.get().getMatches();
    }

    @Override
    public void deleteTournament(long id) throws EntityNotFoundException {
        logger.debug("Deleting tournament with id {}", id);
        if (tournamentDao.findById(id).isEmpty()) {
            logger.error("Tournament with id {} doesn't exist", id);
            throw new EntityNotFoundException("Tournament doesn't exist");
        }
        tournamentDao.deleteById(id);
    }

}
