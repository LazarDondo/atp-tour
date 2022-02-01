package com.silab.atptour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silab.atptour.AtpTourApplication;
import com.silab.atptour.dao.CountryDao;
import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Country;
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

    @Autowired
    CountryDao countryDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    MatchDao matchDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private Match firstMatch;
    private Match secondMatch;
    private Match thirdMatch;
    private List<Match> testMatches;

    @BeforeEach
    public void init() {
        Country country = countryDao.save(new Country(1, "England", "ENG"));
        Tournament tournament = tournamentDao.save(new Tournament(1, "Wimbledon", LocalDate.of(2022, Month.JULY, 10),
                LocalDate.of(2022, Month.JULY, 24), country, "Grand Slam", null));

        Player firstPlayer = playerDao.save(new Player(1));
        Player secondPlayer = playerDao.save(new Player(2));
        Player thirdPlayer = playerDao.save(new Player(3));

        testMatches = new ArrayList<>();
        firstMatch = matchDao.save(new Match(tournament, firstPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 10), "2. round", "3-0", firstPlayer));
        secondMatch = matchDao.save(new Match(tournament, firstPlayer, thirdPlayer, LocalDate.of(2022, Month.JULY, 11), "2. round", "3-2", firstPlayer));
        thirdMatch = matchDao.save(new Match(tournament, thirdPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 12), "2. round", "2-3", secondPlayer));

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
    public void addMatchesShouldBeOk() throws Exception {
        matchDao.deleteAll();
        mockMvc
                .perform(MockMvcRequestBuilders.post("/matches").contentType(MediaType.APPLICATION_JSON)
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
    public void addMatchesShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/matches"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void addMatchesShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/matches"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateMatchesShouldBeOk() throws Exception {
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
    public void getH2HMatchesShouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/matches/" + firstMatch.getFirstPlayer().getId() + "/" + firstMatch.getSecondPlayer().getId()))
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
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getH2HMatchesShouldBeOkForAdminUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/matches/" + firstMatch.getFirstPlayer().getId() + "/" + firstMatch.getSecondPlayer().getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getH2HMatchesShouldBeNotFoundForFirstPlayer() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/matches/" + firstMatch.getFirstPlayer().getId() + 55 + "/" + firstMatch.getSecondPlayer().getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getH2HMatchesShouldBeNotFoundForSecondPlayer() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/matches/" + firstMatch.getFirstPlayer().getId() + "/" + firstMatch.getSecondPlayer().getId() + 55))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getH2HMatchesShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/matches/" + firstMatch.getFirstPlayer().getId() + "/" + firstMatch.getSecondPlayer().getId()))
                .andExpect(status().isUnauthorized());
    }
}
