package com.project.scheduler.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@ukma.edu.ua", authorities = "ADMIN")
    void shouldReturnHomePageForAdmin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(username = "teacher@ukma.edu.ua", authorities = "TEACHER")
    void shouldReturnHomePageForTeacher() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(username = "student@ukma.edu.ua", authorities = "STUDENT")
    void shouldReturnHomePageForStudent() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirectToTheLoginPage_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().is(302));
    }

}
