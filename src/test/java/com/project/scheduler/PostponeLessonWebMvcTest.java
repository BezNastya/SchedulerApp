package com.project.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostponeLessonWebMvcTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(get("/postponeLesson"))
                .andExpect(view().name("postponeLesson"))
                .andExpect(model().attributeExists("postponeLesson"));
    }

    @Test
    void whenInvalidResource_thenReturn404() throws Exception {
        mockMvc.perform(get("/wrong")).andExpect(status().isNotFound());
    }

    @Test
    void whenTryGetAccessWithoutAuthentication_thenReturn302() throws Exception {
        mockMvc.perform(get("/admin/1")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin@ukma.edu.ua", password = "admin", authorities = "ADMIN")
    void whenTryGetAuthorizedAccess_thenReturn200() throws Exception {
        mockMvc.perform(get("/admin/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("email\":\"admin@ukma.edu.ua")));
    }

    @Test
    @WithMockUser(username = "admin@ukma.edu.ua", password = "admin", authorities = "ADMIN")
    void whenTryGetUnauthorizedAccess_thenReturn403() throws Exception {
        mockMvc.perform(get("/teacher/1")).andExpect(status().isForbidden());
    }
}