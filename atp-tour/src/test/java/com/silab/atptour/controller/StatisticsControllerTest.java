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

    private Match testMatch;
    private Statistics testStatistics;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private MatchDao matchDao;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        Country country = countryDao.save(new Country(1, "England", "ENG"));
        Tournament tournament = tournamentDao.save(new Tournament(1, "Wimbledon", LocalDate.of(2022, Month.JULY, 10),
                LocalDate.of(2022, Month.JULY, 16), country, "Grand Slam", null, null, null));

        Player firstPlayer = playerDao.save(new Player(1));
        Player secondPlayer = playerDao.save(new Player(2));

        testMatch = matchDao.save(new Match(tournament, firstPlayer, secondPlayer, LocalDate.of(2022, Month.JULY, 10), "2. round", "3-0", firstPlayer));

        testStatistics = statisticsDao.save(new Statistics(1, testMatch, 50, 30, 5, 3, 6, 2, 50, 30, 20, 10));
    }

    @AfterEach
    public void destroy() {
        statisticsDao.deleteAll();
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void saveStatisticsStatisticsShouldBeOk() throws Exception {
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
    public void saveStatisticsShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void saveStatisticsShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/statistics").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void findStatisticsShouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/statistics/find").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testMatch)))
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
    public void findStatisticsShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/statistics/find").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testStatistics)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void findStatisticsShouldBeOkForAdminUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/statistics/find").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testMatch)))
                .andExpect(status().isOk());
    }

}
