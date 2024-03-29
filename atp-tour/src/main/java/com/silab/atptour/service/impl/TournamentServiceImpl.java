package com.silab.atptour.service.impl;

import com.silab.atptour.dao.IncomeDao;
import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Income;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.model.AtpModel;
import com.silab.atptour.service.TournamentService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represent an implementation of the {@link TournamentService} interface
 *
 * @author Lazar
 */
@Service
@Transactional
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private IncomeDao incomeDao;

    private final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament addTournament(Tournament tournament) throws AtpEntityExistsException {
        String name = tournament.getName() + "-" + tournament.getStartDate().getYear();
        tournament.setName(name);
        if (tournamentDao.findTournamentByName(name).isPresent()) {
            throw new AtpEntityExistsException("Tournament with name " + name + " already exists");
        }

        tournament.setCompletionDate(tournament.getStartDate().plusDays(6));
        logger.debug("Adding new {} tournament", tournament.getName());

        Tournament savedTournament = tournamentDao.save(tournament);
        if (savedTournament.getParticipants() != null && !savedTournament.getParticipants().isEmpty()) {
            createMatches(savedTournament);
            createIncomes(savedTournament);
        }
        return savedTournament;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament updateTournament(Tournament tournament) throws AtpEntityNotFoundException, AtpEntityExistsException {
        logger.debug("Finding tournament {}", tournament.getName());
        Optional<Tournament> optionalTournament = tournamentDao.findById(tournament.getId());
        String name = tournament.getName();
        if (optionalTournament.isEmpty()) {
            throw new AtpEntityNotFoundException("Tournament " + tournament.getName() + " doesn't exist");
        }
        name += "-" + tournament.getStartDate().getYear();
        if (!optionalTournament.get().getName().equals(tournament.getName()) && tournamentDao.findTournamentByName(name).isPresent()) {
            throw new AtpEntityExistsException("Tournament with name " + name + " already exists");
        }
        if (!optionalTournament.get().getName().equals(tournament.getName())) {
            tournament.setName(name);
        }
        if (!optionalTournament.get().getStartDate().equals(tournament.getStartDate())) {
            updateTournamentDates(tournament);
        }
        tournament.setParticipants(tournamentDao.findParticipants(tournament.getId()));

        logger.debug("Updating tournament {}", tournament.getName());
        return tournamentDao.save(tournament);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tournament getTournament(long id) throws AtpEntityNotFoundException {
        Optional<Tournament> optionalTournament = tournamentDao.findById(id);
        if (optionalTournament.isEmpty()) {
            throw new AtpEntityNotFoundException("Tournament doesn't exist");
        }
        Tournament tournament = optionalTournament.get();
        tournament.setParticipants(tournamentDao.findParticipants(tournament.getId()));
        return optionalTournament.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Tournament> getAllTournaments(String name, String hostCountry, String tournamentType, Pageable pageable) {
        logger.debug("Retreiving all tournaments");
        return tournamentDao.findAllTournaments(name, hostCountry, tournamentType, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTournament(long id) throws AtpEntityNotFoundException {
        logger.debug("Deleting tournament with id {}", id);
        if (tournamentDao.findById(id).isEmpty()) {
            throw new AtpEntityNotFoundException("Tournament doesn't exist");
        }
        tournamentDao.deleteById(id);
    }

    /**
     * Creates matches based on the participants
     *
     * @param tournament A {@link Tournament} for which the matches will be created
     */
    private void createMatches(Tournament tournament) {
        LocalDate startDate = tournament.getStartDate();
        List<Match> matches = new ArrayList<>();
        List<Player> participants = tournament.getParticipants();
        int size = participants.size() - 1;
        String roundName = AtpModel.GRAND_SLAM_EIGHTS_FINALS;

        for (int i = 0; i <= size / 2; i++) {
            matches.add(new Match(tournament, participants.get(i), participants.get(size - i),
                    startDate.plusDays(i % 2), roundName));
        }

        List<Match> quaterFinals = new ArrayList<>();

        int matchesSize = matches.size() - 1;
        for (int i = 0; i <= matchesSize / 2; i++) {
            Match nextMatch = new Match(tournament,
                    startDate.plusDays((i % 2) + 2), AtpModel.GRAND_SLAM_QUATER_FINALS);
            quaterFinals.add(nextMatch);
            matches.get(i).setNextMatch(nextMatch);
            matches.get(matchesSize - i).setNextMatch(nextMatch);
        }

        matchesSize = quaterFinals.size() - 1;
        Match finals = new Match(tournament,
                tournament.getCompletionDate(), AtpModel.GRAND_SLAM_FINALS);
        for (int i = 0; i <= matchesSize / 2; i++) {
            Match nextMatch = new Match(tournament,
                    quaterFinals.get(i).getMatchDate().plusDays((i % 2) + 1), AtpModel.GRAND_SLAM_SEMI_FINALS);
            nextMatch.setNextMatch(finals);
            quaterFinals.add(nextMatch);
            quaterFinals.get(i).setNextMatch(nextMatch);
            quaterFinals.get(matchesSize - i).setNextMatch(nextMatch);
        }

        matchDao.saveAll(matches);
    }

    /**
     * Updates tournament's completion date and matches start date
     *
     * @param tournament A {@link Tournament} for which the dates will be updated
     */
    private void updateTournamentDates(Tournament tournament) {
        LocalDate startDate = tournament.getStartDate();
        tournament.setCompletionDate(startDate.plusDays(7));
        Pageable pageable = Pageable.ofSize(Integer.MAX_VALUE);
        List<Match> matches = matchDao.filterMatches(tournament, null, null, pageable).getContent();

        for (int i = 0; i < matches.size() / 2; i++) {
            matches.get(i).setMatchDate(startDate);
        }

        startDate = startDate.plusDays(1);
        for (int i = matches.size() / 2; i < matches.size(); i++) {
            matches.get(i).setMatchDate(startDate);
        }
        matchDao.saveAll(matches);
    }

    /**
     * Creates income for the given tournament for all of the participants
     *
     * @param tournament A {@link Tournament} for which incomes will be created
     */
    private void createIncomes(Tournament tournament) {
        List<Income> incomes = new ArrayList<>();
        List<Player> participants = tournament.getParticipants();
        participants.stream().forEach(player -> incomes.add(new Income(tournament, player, 0)));
        incomeDao.saveAll(incomes);
    }

}
