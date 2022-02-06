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
public class TournamentControllerTest {

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

    private Tournament testTournament;

    @BeforeEach
    public void init() {
        Country country = countryDao.save(new Country(1, "England", "ENG"));
        testTournament = tournamentDao.save(new Tournament(1, "Wimbledon", LocalDate.of(2022, Month.JULY, 10),
                LocalDate.of(2022, Month.JULY, 17), country, "Grand Slam", null));
    }

    @AfterEach
    public void destroy() {
        tournamentDao.deleteAll();
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void addTournamentShouldBeOk() throws Exception {
        Country country = countryDao.save(new Country(2, "France", "FRA"));
        Tournament tournament = new Tournament(2, "Roland Garros", LocalDate.of(2022, Month.MAY, 10),
                LocalDate.of(2022, Month.MAY, 17), country, "Grand Slam", null);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/tournament").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(tournament)))
                .andExpect(jsonPath("$.name", is(tournament.getName() + "-" + tournament.getStartDate().getYear())))
                .andExpect(jsonPath("$.startDate", is(tournament.getStartDate().toString())))
                .andExpect(jsonPath("$.completitionDate", is(tournament.getCompletitionDate().toString())))
                .andExpect(jsonPath("$.hostCountry.name", is(tournament.getHostCountry().getName())))
                .andExpect(jsonPath("$.tournamentType", is(tournament.getTournamentType())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void addTournamentShouldBeConflict() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/tournament").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testTournament)))
                .andExpect(status().isOk());
    }

    @Test
    public void addTournamentShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/tournament"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void addTournamentShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/tournament"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateTournamentShouldBeOk() throws Exception {
        testTournament.setName(testTournament.getName() + "-" + testTournament.getStartDate().getYear());
        Tournament tournament = tournamentDao.save(testTournament);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/tournament").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(tournament)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(tournament.getName())))
                .andExpect(jsonPath("$.startDate", is(tournament.getStartDate().toString())))
                .andExpect(jsonPath("$.completitionDate", is(tournament.getCompletitionDate().toString())))
                .andExpect(jsonPath("$.hostCountry.name", is(tournament.getHostCountry().getName())))
                .andExpect(jsonPath("$.tournamentType", is(tournament.getTournamentType())));
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateTournamentShouldBeNotFound() throws Exception {
        testTournament.setId(55);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/tournament").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testTournament)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateTournamentShouldBeConflict() throws Exception {
        Tournament tournament = new Tournament();
        tournament.setName("Roland Garros-2022");
        tournament = tournamentDao.save(tournament);
        testTournament.setName("Roland Garros");
        testTournament.setStartDate(LocalDate.of(2022, Month.MAY, 10));
        mockMvc
                .perform(MockMvcRequestBuilders.put("/tournament").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testTournament)))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateTournamentShouldBeUnauthorized() throws Exception {
        testTournament.setId(2);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/tournament"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void updateTournamentShouldBeForbidden() throws Exception {
        testTournament.setId(2);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/tournament"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getTournamentByIdShouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testTournament.getName())))
                .andExpect(jsonPath("$.startDate", is(testTournament.getStartDate().toString())))
                .andExpect(jsonPath("$.completitionDate", is(testTournament.getCompletitionDate().toString())))
                .andExpect(jsonPath("$.hostCountry.name", is(testTournament.getHostCountry().getName())))
                .andExpect(jsonPath("$.tournamentType", is(testTournament.getTournamentType())));
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getTournamentByIdShouldBeOkForAdminUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getTournamentByIdShouldBeNotFound() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId() + 55))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTournamentByIdShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getTournamentsShouldBeOk() throws Exception {
        Tournament tournament = tournamentDao.save(new Tournament(2, "Roland Garros", LocalDate.of(2022, Month.MAY, 10),
                LocalDate.of(2022, Month.MAY, 24), new Country(2, "France", "FRA"), "Grand Slam", null));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]name", is(testTournament.getName())))
                .andExpect(jsonPath("$.[0]startDate", is(testTournament.getStartDate().toString())))
                .andExpect(jsonPath("$.[0]completitionDate", is(testTournament.getCompletitionDate().toString())))
                .andExpect(jsonPath("$.[0]hostCountry.name", is(testTournament.getHostCountry().getName())))
                .andExpect(jsonPath("$.[0]tournamentType", is(testTournament.getTournamentType())))
                
                .andExpect(jsonPath("$.[1]name", is(tournament.getName())))
                .andExpect(jsonPath("$.[1]startDate", is(tournament.getStartDate().toString())))
                .andExpect(jsonPath("$.[1]completitionDate", is(tournament.getCompletitionDate().toString())))
                .andExpect(jsonPath("$.[1]hostCountry.name", is(tournament.getHostCountry().getName())))
                .andExpect(jsonPath("$.[1]tournamentType", is(tournament.getTournamentType())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getTournamentsShouldBeOkForAdminUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTournamentsShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getMatchesShouldBeOk() throws Exception {

        Player firstPlayer = playerDao.save(new Player(1));
        Player secondPlayer = playerDao.save(new Player(2));
        Player thirdPlayer = playerDao.save(new Player(3));

        Match firstMatch = matchDao.save(new Match(testTournament, firstPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 10), "2. round", "3-0", firstPlayer));
        Match secondMatch = matchDao.save(new Match(testTournament, firstPlayer, thirdPlayer, LocalDate.of(2022, Month.JULY, 11), "2. round", "3-2", firstPlayer));
        Match thirdMatch = matchDao.save(new Match(testTournament, thirdPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 12), "2. round", "2-3", secondPlayer));

        List<Match> matches = new ArrayList<>();
        matches.add(firstMatch);
        matches.add(secondMatch);
        matches.add(thirdMatch);

        testTournament.setMatches(matches);
        tournamentDao.save(testTournament);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId() + "/matches"))
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
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getMatchesShouldBeOkForAdminUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId() + "/matches"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getMatchesShouldBeNotFound() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId() + 55 + "/matches"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMatchesShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId() + 55 + "/matches"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void deleteTournamentShouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tournament/" + testTournament.getId()))
                .andExpect(status().isOk());
        mockMvc
                .perform(MockMvcRequestBuilders.get("/tournament/" + testTournament.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void deleteTournamentShouldBeNotFound() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tournament/" + testTournament.getId() + 55))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTournamentShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tournament/" + testTournament.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void deleteTournamentShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/tournament/" + testTournament.getId()))
                .andExpect(status().isForbidden());
    }

}
