package com.silab.atptour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silab.atptour.AtpTourApplication;
import com.silab.atptour.dao.CountryDao;
import com.silab.atptour.dao.PlayerDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.entity.Player;
import java.time.LocalDate;
import java.time.Month;
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

    private Country testCountry;
    private Player testPlayer;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, 0, "Serbia", "SRB"));
        testPlayer = playerDao.save(new Player(1, 0, "Novak", "Djokovic", testCountry, LocalDate.of(2022, Month.MAY, 22), 12000,
                12000, 1, null, null));
    }

    @AfterEach
    public void destroy() {
        playerDao.deleteAll();
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void addPlayerShouldBeOk() throws Exception {
        Player player = new Player(2, 0, "Filip", "Krajinovic", testCountry,
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2, null, null);

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
        Player player = playerDao.save(new Player(2, 0, "Filip", "Krajinovic", testCountry,
                LocalDate.of(1992, Month.SEPTEMBER, 27), 10000, 10000, 2, null, null));
        playerDao.save(new Player(3, 0, "Test", "Test", testCountry,
                LocalDate.of(1995, Month.JANUARY, 12), 0, 0, 0, null, null));
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
}
