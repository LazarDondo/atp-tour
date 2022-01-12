package com.silab.atptour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silab.atptour.AtpTourApplication;
import com.silab.atptour.dao.CountryDao;
import com.silab.atptour.dao.MatchDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.dao.StatisticsDao;
import com.silab.atptour.dao.TournamentDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Match;
import com.silab.atptour.entity.Player;
import com.silab.atptour.entity.Statistics;
import com.silab.atptour.entity.Tournament;
import java.time.LocalDate;
import java.time.Month;
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
public class StatisticsControllerTest {

    @Autowired
    CountryDao countryDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    MatchDao matchDao;

    @Autowired
    StatisticsDao statisticsDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private Statistics testStatistics;

    @BeforeEach
    public void init() {
        Country country = countryDao.save(new Country(1, "England", "ENG"));
        Tournament tournament = tournamentDao.save(new Tournament(1, "Wimbledon", LocalDate.of(2022, Month.JULY, 10),
                LocalDate.of(2022, Month.JULY, 24), country, "Grand Slam", null));

        Player firstPlayer = playerDao.save(new Player(1));
        Player secondPlayer = playerDao.save(new Player(2));

        Match match = matchDao.save(new Match(tournament, firstPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 10), "2. round", "3-0", firstPlayer));

        testStatistics = statisticsDao.save(new Statistics(1, match, 50, 30, 5, 3, 6, 2, 50, 30, 20, 10));
    }
    
    @AfterEach
    public void destroy(){
        statisticsDao.deleteAll();
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void addStatisticsShouldBeOk() throws Exception {      
        mockMvc
                .perform(MockMvcRequestBuilders.post("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(jsonPath("$.firstPlayerPoints", is(testStatistics.getFirstPlayerPoints())))
                .andExpect(jsonPath("$.secondPlayerPoints", is(testStatistics.getSecondPlayerPoints())))
                .andExpect(jsonPath("$.firstPlayerAces", is(testStatistics.getFirstPlayerAces())))
                .andExpect(jsonPath("$.secondPlayerAces", is(testStatistics.getSecondPlayerAces())))
                .andExpect(jsonPath("$.firstPlayerBreakPoints", is(testStatistics.getFirstPlayerBreakPoints())))
                .andExpect(jsonPath("$.secondPlayerBreakPoints", is(testStatistics.getSecondPlayerBreakPoints())))
                .andExpect(jsonPath("$.firstPlayerFirstServesIn", is(testStatistics.getFirstPlayerFirstServesIn())))
                .andExpect(jsonPath("$.secondPlayerFirstServesIn", is(testStatistics.getSecondPlayerFirstServesIn())))
                .andExpect(jsonPath("$.firstPlayerSecondServesIn", is(testStatistics.getFirstPlayerSecondServesIn())))
                .andExpect(jsonPath("$.secondPlayerSecondServesIn", is(testStatistics.getSecondPlayerSecondServesIn())))
                .andExpect(status().isOk());
    }

    @Test
    public void addStatisticsShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void addStatisticsShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateStatisticsShouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(jsonPath("$.firstPlayerPoints", is(testStatistics.getFirstPlayerPoints())))
                .andExpect(jsonPath("$.secondPlayerPoints", is(testStatistics.getSecondPlayerPoints())))
                .andExpect(jsonPath("$.firstPlayerAces", is(testStatistics.getFirstPlayerAces())))
                .andExpect(jsonPath("$.secondPlayerAces", is(testStatistics.getSecondPlayerAces())))
                .andExpect(jsonPath("$.firstPlayerBreakPoints", is(testStatistics.getFirstPlayerBreakPoints())))
                .andExpect(jsonPath("$.secondPlayerBreakPoints", is(testStatistics.getSecondPlayerBreakPoints())))
                .andExpect(jsonPath("$.firstPlayerFirstServesIn", is(testStatistics.getFirstPlayerFirstServesIn())))
                .andExpect(jsonPath("$.secondPlayerFirstServesIn", is(testStatistics.getSecondPlayerFirstServesIn())))
                .andExpect(jsonPath("$.firstPlayerSecondServesIn", is(testStatistics.getFirstPlayerSecondServesIn())))
                .andExpect(jsonPath("$.secondPlayerSecondServesIn", is(testStatistics.getSecondPlayerSecondServesIn())))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateStatisticsShouldBeNotFound() throws Exception {
        testStatistics.setId(55);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStatisticsShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void updateStatisticsShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(status().isForbidden());
    }

}
