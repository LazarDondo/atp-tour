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
public class PlayerControllerTest {

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

    private Country testCountry;
    private Player testPlayer;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, "Serbia", "SRB"));
        testPlayer = playerDao.save(new Player(1, "Novak", "Djokovic", testCountry, LocalDate.of(2022, Month.MAY, 22), 12000, 12000, 1,null));
    }

    @AfterEach
    public void destroy() {
        playerDao.deleteAll();
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void addPlayerShouldBeOk() throws Exception {
        Player player = new Player(2, "Filip", "Krajinovic", testCountry,
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2,null);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/player").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(player)))
                .andExpect(jsonPath("$.firstName", is(player.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(player.getLastName())))
                .andExpect(jsonPath("$.birthCountry.name", is(player.getBirthCountry().getName())))
                .andExpect(jsonPath("$.dateOfBirth", is(player.getDateOfBirth().toString())))
                .andExpect(jsonPath("$.currentPoints", is(player.getCurrentPoints())))
                .andExpect(jsonPath("$.livePoints", is(player.getLivePoints())))
                .andExpect(jsonPath("$.rank", is(player.getRank())))
                .andExpect(status().isOk());
    }

    @Test
    public void addPlayerShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/player"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void addPlayerShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/player"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updatePlayerShouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/player").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testPlayer)))
                .andExpect(jsonPath("$.firstName", is(testPlayer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testPlayer.getLastName())))
                .andExpect(jsonPath("$.birthCountry.name", is(testPlayer.getBirthCountry().getName())))
                .andExpect(jsonPath("$.dateOfBirth", is(testPlayer.getDateOfBirth().toString())))
                .andExpect(jsonPath("$.currentPoints", is(testPlayer.getCurrentPoints())))
                .andExpect(jsonPath("$.livePoints", is(testPlayer.getLivePoints())))
                .andExpect(jsonPath("$.rank", is(testPlayer.getRank())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updatePlayerShouldBeNotFound() throws Exception {
        testPlayer.setId(55);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/player").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testPlayer)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePlayerShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/player"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void updatePlayerShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/player"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getPlayerByIdShouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player/" + testPlayer.getId()))
                .andExpect(jsonPath("$.firstName", is(testPlayer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testPlayer.getLastName())))
                .andExpect(jsonPath("$.birthCountry.name", is(testPlayer.getBirthCountry().getName())))
                .andExpect(jsonPath("$.dateOfBirth", is(testPlayer.getDateOfBirth().toString())))
                .andExpect(jsonPath("$.currentPoints", is(testPlayer.getCurrentPoints())))
                .andExpect(jsonPath("$.livePoints", is(testPlayer.getLivePoints())))
                .andExpect(jsonPath("$.rank", is(testPlayer.getRank())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getPlayerByIdShouldBeOkForAdminUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player/" + testPlayer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getPlayerByIdShouldBeNotFound() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player/" + testPlayer.getId() + 55))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPlayerByIdShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player/" + testPlayer.getId() + 55))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getPlayersShouldBeOk() throws Exception {
        Player player = playerDao.save(new Player(2, "Filip", "Krajinovic", testCountry,
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2,null));
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]firstName", is(testPlayer.getFirstName())))
                .andExpect(jsonPath("$.[0]lastName", is(testPlayer.getLastName())))
                .andExpect(jsonPath("$.[0]birthCountry.name", is(testPlayer.getBirthCountry().getName())))
                .andExpect(jsonPath("$.[0]dateOfBirth", is(testPlayer.getDateOfBirth().toString())))
                .andExpect(jsonPath("$.[0]currentPoints", is(testPlayer.getCurrentPoints())))
                .andExpect(jsonPath("$.[0]livePoints", is(testPlayer.getLivePoints())))
                .andExpect(jsonPath("$.[0]rank", is(testPlayer.getRank())))
                
                .andExpect(jsonPath("$.[1]firstName", is(player.getFirstName())))
                .andExpect(jsonPath("$.[1]lastName", is(player.getLastName())))
                .andExpect(jsonPath("$.[1]birthCountry.name", is(player.getBirthCountry().getName())))
                .andExpect(jsonPath("$.[1]dateOfBirth", is(player.getDateOfBirth().toString())))
                .andExpect(jsonPath("$.[1]currentPoints", is(player.getCurrentPoints())))
                .andExpect(jsonPath("$.[1]livePoints", is(player.getLivePoints())))
                .andExpect(jsonPath("$.[1]rank", is(player.getRank())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getPlayersShouldBeOkForAdminUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPlayersShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getMatchesShouldBeOk() throws Exception {

        Tournament tournament = tournamentDao.save(new Tournament(1, "Serbia Open", LocalDate.of(2022, Month.JULY, 10),
                LocalDate.of(2022, Month.JULY, 24), testCountry, "Grand Slam", null));
        Player secondPlayer = playerDao.save(new Player(2, 2));
        Player thirdPlayer = playerDao.save(new Player(3, 3));

        Match firstMatch = matchDao.save(new Match(tournament, testPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 10), "2. round", "3-0", testPlayer));
        Match secondMatch = matchDao.save(new Match(tournament, testPlayer, thirdPlayer, LocalDate.of(2022, Month.JULY, 11), "2. round", "3-2", testPlayer));

        List<Match> matches = new ArrayList<>();
        matches.add(firstMatch);
        matches.add(secondMatch);

        testPlayer.setMatches(matches);
        playerDao.save(testPlayer);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/player/" + testPlayer.getId() + "/matches"))
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
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getMatchesShouldBeOkForAdminUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player/" + testPlayer.getId() + "/matches"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getMatchesShouldBeNotFound() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player/" + testPlayer.getId() + 55 + "/matches"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMatchesShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/player/" + testPlayer.getId() + "/matches"))
                .andExpect(status().isUnauthorized());
    }

}
