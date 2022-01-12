package com.silab.atptour.controller;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silab.atptour.AtpTourApplication;
import com.silab.atptour.dao.RoleDao;
import com.silab.atptour.dao.UserDao;
import com.silab.atptour.entity.Role;
import com.silab.atptour.entity.User;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserControllerTest {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private User testUser;

    @BeforeEach
    public void init() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, "ADMIN"));
        testUser = userDao.save(new User(1, "homersimpson@gmail.com", passwordEncoder.encode("maxpower"), "Homer", "Simpson", true, null));
        mapper.disable(MapperFeature.USE_ANNOTATIONS);
    }
    
    @AfterEach
    public void destroy(){
        userDao.deleteAll();
    }

    @Test
    public void loginShouldBeOk() throws Exception {
        testUser.setPassword("maxpower");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(testUser.getUsername())))
                .andExpect(jsonPath("$.firstName", is(testUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testUser.getLastName())))
                .andExpect(jsonPath("$.enabled", is(testUser.isEnabled())));
    }

    @Test
    public void loginShouldBeNotFoundInvalidUsername() throws Exception {
        testUser.setUsername("bartsimpson@gmail.com");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void loginShouldBeNotFoundInvalidPassword() throws Exception {
        testUser.setPassword("elbarto");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void registerShouldBeOk() throws Exception {
        User user = new User(2, "bartsimpson@gmail.com", "elbarto", "Bart", "Simpson", true, null);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/user/register").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.enabled", is(user.isEnabled())));
    }

    @Test
    public void registerShouldBeConflictDuplicateUsername() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/user/register").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testUser)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateUserShouldBeOk() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(testUser.getUsername())))
                .andExpect(jsonPath("$.firstName", is(testUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testUser.getLastName())))
                .andExpect(jsonPath("$.enabled", is(testUser.isEnabled())));
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateUserShouldBeNotFound() throws Exception {
        testUser.setId(55);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "ADMIN")
    public void updateUserShouldBeConflictDuplicateUsername() throws Exception {
        User user = new User();
        user.setUsername("bartsimpson@gmail.com");
        user = userDao.save(user);
        testUser.setUsername("bartsimpson@gmail.com");
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testUser)))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateUserShouldBeUnauthorized() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test", password = "test", authorities = "USER")
    public void updateUserShouldForbidden() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user"))
                .andExpect(status().isForbidden());
    }
}
