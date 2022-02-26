package com.silab.atptour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silab.atptour.AtpTourApplication;
import com.silab.atptour.dao.CountryDao;
import com.silab.atptour.dao.IncomeDao;
import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Income;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Tournament;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Lazar
 */
@SpringBootTest(classes = AtpTourApplication.class)
@AutoConfigureMockMvc
public class MatchControllerTest {

    private Match firstMatch;
    private Match secondMatch;
    private Match thirdMatch;
    private Match otherTournamentMatch;
    private List<Match> testMatches;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private IncomeDao incomeDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        Country country = countryDao.save(new Country(1, "England", "ENG"));
        Tournament tournament = tournamentDao.save(new Tournament(1, "Wimbledon", LocalDate.of(2022, Month.JULY, 10),
                LocalDate.of(2022, Month.JULY, 16), country, "Grand Slam", null, null, null));

        Tournament otherTournament = tournamentDao.save(new Tournament(2, "Roland Garros", LocalDate.of(2022, Month.MAY, 22),
                LocalDate.of(2022, Month.JULY, 28), country, "Grand Slam", null, null, null));

        Player firstPlayer = playerDao.save(new Player(1, "Novak", "Djokovic", country, LocalDate.of(2022, Month.MAY, 22), 12000,
                12000, 1, null, null));
        Player secondPlayer = playerDao.save(new Player(2, "Filip", "Krajinovic", country,
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2, null, null));
        Player thirdPlayer = playerDao.save(new Player(3, "Miomir", "Kecmanovic", country, LocalDate.of(1999, Month.AUGUST, 31),
                5000, 5000, 3, null, null));

        testMatches = new ArrayList<>();
        firstMatch = matchDao.save(new Match(tournament, firstPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 10),
                "finals", "3-0", firstPlayer));
        secondMatch = matchDao.save(new Match(tournament, firstPlayer, thirdPlayer, LocalDate.of(2022, Month.JULY, 11),
                "semi-finals", "3-2", firstPlayer));
        thirdMatch = matchDao.save(new Match(tournament, thirdPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 12),
                "quater-finals", "1-3", secondPlayer));
        otherTournamentMatch = matchDao.save(new Match(otherTournament, firstPlayer, secondPlayer,
                LocalDate.of(2022, Month.JULY, 22), "eights-finals", "2-3", secondPlayer));

        testMatches.add(firstMatch);
        testMatches.add(secondMatch);
        testMatches.add(thirdMatch);
    }

    @AfterEach
    public void destroy() {
        matchDao.deleteAll();
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateMatchesShouldBeOk() throws Exception {

        incomeDao.save(new Income(firstMatch.getTournament(), firstMatch.getFirstPlayer(), 0));
        incomeDao.save(new Income(firstMatch.getTournament(), firstMatch.getSecondPlayer(), 0));
        incomeDao.save(new Income(secondMatch.getTournament(), secondMatch.getSecondPlayer(), 0));
        mockMvc
                .perform(MockMvcRequestBuilders.put("/matches").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testMatches)))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0]tournament.name", is(firstMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[0]firstPlayer.firstName", is(firstMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]firstPlayer.lastName", is(firstMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]secondPlayer.firstName", is(firstMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]secondPlayer.lastName", is(firstMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]matchDate", is(firstMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[0]round", is(firstMatch.getRound())))
                .andExpect(jsonPath("$.[0]result", is(firstMatch.getResult())))
                .andExpect(jsonPath("$.[0]winner.firstName", is(firstMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[0]winner.lastName", is(firstMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[1]tournament.name", is(secondMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[1]firstPlayer.firstName", is(secondMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]firstPlayer.lastName", is(secondMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]secondPlayer.firstName", is(secondMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]secondPlayer.lastName", is(secondMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]matchDate", is(secondMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[1]round", is(secondMatch.getRound())))
                .andExpect(jsonPath("$.[1]result", is(secondMatch.getResult())))
                .andExpect(jsonPath("$.[1]winner.firstName", is(secondMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[1]winner.lastName", is(secondMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[2]tournament.name", is(thirdMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[2]firstPlayer.firstName", is(thirdMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[2]firstPlayer.lastName", is(thirdMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[2]secondPlayer.firstName", is(thirdMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[2]secondPlayer.lastName", is(thirdMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[2]matchDate", is(thirdMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[2]round", is(thirdMatch.getRound())))
                .andExpect(jsonPath("$.[2]result", is(thirdMatch.getResult())))
                .andExpect(jsonPath("$.[2]winner.firstName", is(thirdMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[2]winner.lastName", is(thirdMatch.getWinner().getLastName())))
                .andExpect(status().isOk());
    }

    @Test
    public void updateMatchesShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/matches"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void updateMatchesShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/matches"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void filterMatchesShouldBeOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(firstMatch)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0]tournament.name", is(firstMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[0]firstPlayer.firstName", is(firstMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]firstPlayer.lastName", is(firstMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]secondPlayer.firstName", is(firstMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]secondPlayer.lastName", is(firstMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]matchDate", is(firstMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[0]round", is(firstMatch.getRound())))
                .andExpect(jsonPath("$.[0]result", is(firstMatch.getResult())))
                .andExpect(jsonPath("$.[0]winner.firstName", is(firstMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[0]winner.lastName", is(firstMatch.getWinner().getLastName())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void filterAllMatchesBetweenPlayersShouldBeOk() throws Exception {
        secondMatch.setSecondPlayer(firstMatch.getSecondPlayer());
        secondMatch.setTournament(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secondMatch)))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]tournament.name", is(firstMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[0]firstPlayer.firstName", is(firstMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]firstPlayer.lastName", is(firstMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]secondPlayer.firstName", is(firstMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]secondPlayer.lastName", is(firstMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]matchDate", is(firstMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[0]round", is(firstMatch.getRound())))
                .andExpect(jsonPath("$.[0]result", is(firstMatch.getResult())))
                .andExpect(jsonPath("$.[0]winner.firstName", is(firstMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[0]winner.lastName", is(firstMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[1]tournament.name", is(otherTournamentMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[1]firstPlayer.firstName", is(otherTournamentMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]firstPlayer.lastName", is(otherTournamentMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]secondPlayer.firstName", is(otherTournamentMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]secondPlayer.lastName", is(otherTournamentMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]matchDate", is(otherTournamentMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[1]round", is(otherTournamentMatch.getRound())))
                .andExpect(jsonPath("$.[1]result", is(otherTournamentMatch.getResult())))
                .andExpect(jsonPath("$.[1]winner.firstName", is(otherTournamentMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[1]winner.lastName", is(otherTournamentMatch.getWinner().getLastName())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void filterFirstPlayerMatchesShouldBeOk() throws Exception {
        thirdMatch.setFirstPlayer(firstMatch.getFirstPlayer());
        thirdMatch.setSecondPlayer(null);
        thirdMatch.setTournament(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(thirdMatch)))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0]tournament.name", is(firstMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[0]firstPlayer.firstName", is(firstMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]firstPlayer.lastName", is(firstMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]secondPlayer.firstName", is(firstMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]secondPlayer.lastName", is(firstMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]matchDate", is(firstMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[0]round", is(firstMatch.getRound())))
                .andExpect(jsonPath("$.[0]result", is(firstMatch.getResult())))
                .andExpect(jsonPath("$.[0]winner.firstName", is(firstMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[0]winner.lastName", is(firstMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[1]tournament.name", is(secondMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[1]firstPlayer.firstName", is(secondMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]firstPlayer.lastName", is(secondMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]secondPlayer.firstName", is(secondMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]secondPlayer.lastName", is(secondMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]matchDate", is(secondMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[1]round", is(secondMatch.getRound())))
                .andExpect(jsonPath("$.[1]result", is(secondMatch.getResult())))
                .andExpect(jsonPath("$.[1]winner.firstName", is(secondMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[1]winner.lastName", is(secondMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[2]tournament.name", is(otherTournamentMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[2]firstPlayer.firstName", is(otherTournamentMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[2]firstPlayer.lastName", is(otherTournamentMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[2]secondPlayer.firstName", is(otherTournamentMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[2]secondPlayer.lastName", is(otherTournamentMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[2]matchDate", is(otherTournamentMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[2]round", is(otherTournamentMatch.getRound())))
                .andExpect(jsonPath("$.[2]result", is(otherTournamentMatch.getResult())))
                .andExpect(jsonPath("$.[2]winner.firstName", is(otherTournamentMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[2]winner.lastName", is(otherTournamentMatch.getWinner().getLastName())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void filterFirstPlayerMatchesOnTournamentShouldBeOk() throws Exception {
        thirdMatch.setFirstPlayer(firstMatch.getFirstPlayer());
        thirdMatch.setSecondPlayer(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(thirdMatch)))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]tournament.name", is(firstMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[0]firstPlayer.firstName", is(firstMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]firstPlayer.lastName", is(firstMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]secondPlayer.firstName", is(firstMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]secondPlayer.lastName", is(firstMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]matchDate", is(firstMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[0]round", is(firstMatch.getRound())))
                .andExpect(jsonPath("$.[0]result", is(firstMatch.getResult())))
                .andExpect(jsonPath("$.[0]winner.firstName", is(firstMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[0]winner.lastName", is(firstMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[1]tournament.name", is(secondMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[1]firstPlayer.firstName", is(secondMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]firstPlayer.lastName", is(secondMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]secondPlayer.firstName", is(secondMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]secondPlayer.lastName", is(secondMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]matchDate", is(secondMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[1]round", is(secondMatch.getRound())))
                .andExpect(jsonPath("$.[1]result", is(secondMatch.getResult())))
                .andExpect(jsonPath("$.[1]winner.firstName", is(secondMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[1]winner.lastName", is(secondMatch.getWinner().getLastName())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void filterSecondPlayerMatchesShouldBeOk() throws Exception {
        secondMatch.setSecondPlayer(firstMatch.getSecondPlayer());
        secondMatch.setFirstPlayer(null);
        secondMatch.setTournament(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secondMatch)))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0]tournament.name", is(firstMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[0]firstPlayer.firstName", is(firstMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]firstPlayer.lastName", is(firstMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]secondPlayer.firstName", is(firstMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]secondPlayer.lastName", is(firstMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]matchDate", is(firstMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[0]round", is(firstMatch.getRound())))
                .andExpect(jsonPath("$.[0]result", is(firstMatch.getResult())))
                .andExpect(jsonPath("$.[0]winner.firstName", is(firstMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[0]winner.lastName", is(firstMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[1]tournament.name", is(thirdMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[1]firstPlayer.firstName", is(thirdMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]firstPlayer.lastName", is(thirdMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]secondPlayer.firstName", is(thirdMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]secondPlayer.lastName", is(thirdMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]matchDate", is(thirdMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[1]round", is(thirdMatch.getRound())))
                .andExpect(jsonPath("$.[1]result", is(thirdMatch.getResult())))
                .andExpect(jsonPath("$.[1]winner.firstName", is(thirdMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[1]winner.lastName", is(thirdMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[2]tournament.name", is(otherTournamentMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[2]firstPlayer.firstName", is(otherTournamentMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[2]firstPlayer.lastName", is(otherTournamentMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[2]secondPlayer.firstName", is(otherTournamentMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[2]secondPlayer.lastName", is(otherTournamentMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[2]matchDate", is(otherTournamentMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[2]round", is(otherTournamentMatch.getRound())))
                .andExpect(jsonPath("$.[2]result", is(otherTournamentMatch.getResult())))
                .andExpect(jsonPath("$.[2]winner.firstName", is(otherTournamentMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[2]winner.lastName", is(otherTournamentMatch.getWinner().getLastName())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void filterSecondPlayerMatchesOnTournamentShouldBeOk() throws Exception {
        secondMatch.setSecondPlayer(firstMatch.getSecondPlayer());
        secondMatch.setFirstPlayer(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(secondMatch)))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]tournament.name", is(firstMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[0]firstPlayer.firstName", is(firstMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]firstPlayer.lastName", is(firstMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]secondPlayer.firstName", is(firstMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]secondPlayer.lastName", is(firstMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]matchDate", is(firstMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[0]round", is(firstMatch.getRound())))
                .andExpect(jsonPath("$.[0]result", is(firstMatch.getResult())))
                .andExpect(jsonPath("$.[0]winner.firstName", is(firstMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[0]winner.lastName", is(firstMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[1]tournament.name", is(thirdMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[1]firstPlayer.firstName", is(thirdMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]firstPlayer.lastName", is(thirdMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]secondPlayer.firstName", is(thirdMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]secondPlayer.lastName", is(thirdMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]matchDate", is(thirdMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[1]round", is(thirdMatch.getRound())))
                .andExpect(jsonPath("$.[1]result", is(thirdMatch.getResult())))
                .andExpect(jsonPath("$.[1]winner.firstName", is(thirdMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[1]winner.lastName", is(thirdMatch.getWinner().getLastName())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void filterAllMatchesShouldBeOk() throws Exception {
        Match match = new Match();
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(match)))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$.[0]tournament.name", is(firstMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[0]firstPlayer.firstName", is(firstMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]firstPlayer.lastName", is(firstMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]secondPlayer.firstName", is(firstMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[0]secondPlayer.lastName", is(firstMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[0]matchDate", is(firstMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[0]round", is(firstMatch.getRound())))
                .andExpect(jsonPath("$.[0]result", is(firstMatch.getResult())))
                .andExpect(jsonPath("$.[0]winner.firstName", is(firstMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[0]winner.lastName", is(firstMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[1]tournament.name", is(secondMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[1]firstPlayer.firstName", is(secondMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]firstPlayer.lastName", is(secondMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]secondPlayer.firstName", is(secondMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[1]secondPlayer.lastName", is(secondMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[1]matchDate", is(secondMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[1]round", is(secondMatch.getRound())))
                .andExpect(jsonPath("$.[1]result", is(secondMatch.getResult())))
                .andExpect(jsonPath("$.[1]winner.firstName", is(secondMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[1]winner.lastName", is(secondMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[2]tournament.name", is(thirdMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[2]firstPlayer.firstName", is(thirdMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[2]firstPlayer.lastName", is(thirdMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[2]secondPlayer.firstName", is(thirdMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[2]secondPlayer.lastName", is(thirdMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[2]matchDate", is(thirdMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[2]round", is(thirdMatch.getRound())))
                .andExpect(jsonPath("$.[2]result", is(thirdMatch.getResult())))
                .andExpect(jsonPath("$.[2]winner.firstName", is(thirdMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[2]winner.lastName", is(thirdMatch.getWinner().getLastName())))
                
                .andExpect(jsonPath("$.[3]tournament.name", is(otherTournamentMatch.getTournament().getName())))
                .andExpect(jsonPath("$.[3]firstPlayer.firstName", is(otherTournamentMatch.getFirstPlayer().getFirstName())))
                .andExpect(jsonPath("$.[3]firstPlayer.lastName", is(otherTournamentMatch.getFirstPlayer().getLastName())))
                .andExpect(jsonPath("$.[3]secondPlayer.firstName", is(otherTournamentMatch.getSecondPlayer().getFirstName())))
                .andExpect(jsonPath("$.[3]secondPlayer.lastName", is(otherTournamentMatch.getSecondPlayer().getLastName())))
                .andExpect(jsonPath("$.[3]matchDate", is(otherTournamentMatch.getMatchDate().toString())))
                .andExpect(jsonPath("$.[3]round", is(otherTournamentMatch.getRound())))
                .andExpect(jsonPath("$.[3]result", is(otherTournamentMatch.getResult())))
                .andExpect(jsonPath("$.[3]winner.firstName", is(otherTournamentMatch.getWinner().getFirstName())))
                .andExpect(jsonPath("$.[3]winner.lastName", is(otherTournamentMatch.getWinner().getLastName())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void filterMatchesShouldBeEmpty() throws Exception {
        Player player = playerDao.save(new Player(4, "Laslo", "Djere", firstMatch.getFirstPlayer().getBirthCountry(),
                LocalDate.of(1996, Month.JUNE, 20), 4000, 4000, 4, null, null));
        matchDao.save(new Match(firstMatch.getTournament(), firstMatch.getSecondPlayer(), player, LocalDate.of(2022, Month.OCTOBER, 3),
                "finals", "2-3", player));
        firstMatch.setSecondPlayer(player);
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(firstMatch)))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getH2HMatchesShouldBeOkForAdminUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(firstMatch)))
                .andExpect(status().isOk());
    }

    @Test
    public void getH2HMatchesShouldBeUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/matches/filter").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(firstMatch)))
                .andExpect(status().isUnauthorized());
    }
}
