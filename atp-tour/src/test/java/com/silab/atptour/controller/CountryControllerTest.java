package com.silab.atptour.controller;

import com.silab.atptour.AtpTourApplication;
import com.silab.atptour.dao.CountryDao;
import com.silab.atptour.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Lazar
 */
@SpringBootTest(classes = AtpTourApplication.class)
@AutoConfigureMockMvc
public class CountryControllerTest {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void getCountriesShouldBeOk() throws Exception {
        Country firstCountry = countryDao.save(new Country(1, "Serbia", "SRB"));
        Country secondCountry = countryDao.save(new Country(2, "Greece", "GRE"));

        mockMvc.perform(MockMvcRequestBuilders.get("/country"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]name", is(firstCountry.getName())))
                .andExpect(jsonPath("$.[0]codeName", is(firstCountry.getCodeName())))
                .andExpect(jsonPath("$.[1]name", is(secondCountry.getName())))
                .andExpect(jsonPath("$.[1]codeName", is(secondCountry.getCodeName())))
                .andExpect(status().isOk());
    }

    @Test
    public void getCountriesShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/country"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void getCountriesShouldBeForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/country"))
                .andExpect(status().isForbidden());
    }
}
