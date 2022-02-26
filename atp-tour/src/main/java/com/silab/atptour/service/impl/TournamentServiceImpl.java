package com.silab.atptour.service.impl;

import com.silab.atptour.dao.IncomeDao;
import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
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
import org.springframework.stereotype.Service;

/**
 *
 * @author Lazar
 */
@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private IncomeDao incomeDao;

    private final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);

    @Override
    public Tournament addTournament(Tournament tournament) throws AtpEntityExistsException {
        String name = tournament.getName() + "-" + tournament.getStartDate().getYear();
        tournament.setName(name);
        if (tournamentDao.findTournamentByName(name).isPresent()) {
            throw new AtpEntityExistsException("Tournament with name " + name + " already exists");
        }

        tournament.setCompletitionDate(tournament.getStartDate().plusDays(6));
        logger.debug("Adding new {} tournament", tournament.getName());

        Tournament savedTournament = tournamentDao.save(tournament);
        if (savedTournament.getParticipants() != null && !savedTournament.getParticipants().isEmpty()) {
            createMatches(savedTournament);
            createIncomes(savedTournament);
            substractPointsFromPreviousTournament(savedTournament);
        }
        return savedTournament;
    }

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
    public void deleteTournament(long id) throws AtpEntityNotFoundException {
        logger.debug("Deleting tournament with id {}", id);
        if (tournamentDao.findById(id).isEmpty()) {
            throw new AtpEntityNotFoundException("Tournament doesn't exist");
        }
        tournamentDao.deleteById(id);
    }

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
        matchDao.saveAll(matches);
    }

    private void substractPointsFromPreviousTournament(Tournament tournament) {
        logger.debug("Substracting points for players who participated in previous year");
        String previousTournamentName = tournament.getName().split("-")[0] + "-" + (tournament.getStartDate().getYear() - 1);
        logger.info(previousTournamentName);
        Optional<Tournament> previousTournament = tournamentDao.findTournamentByName(previousTournamentName);
        
        if (previousTournament.isEmpty()) {
            logger.debug("{} hasn't been held in previous year", previousTournamentName);
            return;
        }
        
        List<Income> playerIncomes = incomeDao.findIncomesByTournament(previousTournament.get());
        for (Income playerIncome : playerIncomes) {
            Player player = playerDao.findById(playerIncome.getPlayer().getId()).get();
            player.setLivePoints(player.getLivePoints() - playerIncome.getPoints());
            playerDao.save(player);
        }
    }

    private void updateTournamentDates(Tournament tournament) {
        LocalDate startDate = tournament.getStartDate();
        tournament.setCompletitionDate(startDate.plusDays(7));
        List<Match> matches = matchDao.filterMatches(tournament, null, null);
        
        for (int i = 0; i < matches.size() / 2; i++) {
            matches.get(i).setMatchDate(startDate);
        }
        
        startDate = startDate.plusDays(1);
        for (int i = matches.size() / 2; i < matches.size(); i++) {
            matches.get(i).setMatchDate(startDate);
        }
        matchDao.saveAll(matches);
    }

    private void createIncomes(Tournament tournament) {
        List<Income> incomes = new ArrayList<>();
        List<Player> participants = tournament.getParticipants();
        participants.stream().forEach(player -> incomes.add(new Income(tournament, player, 0)));
        incomeDao.saveAll(incomes);
    }

}
