package com.project.scheduler.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "teacher@ukma.edu.ua", authorities = "TEACHER")
    void shouldReturnSchedulePageForTeacher() throws Exception {
        mockMvc.perform(get("/my-lessons"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedule"));
    }

    @Test
    @WithMockUser(username = "student@ukma.edu.ua", authorities = "STUDENT")
    void shouldReturnSchedulePageForStudent() throws Exception {
        mockMvc.perform(get("/my-lessons"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedule"));
    }
}
