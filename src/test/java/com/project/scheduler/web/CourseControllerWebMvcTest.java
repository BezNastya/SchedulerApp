package com.project.scheduler.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "teacher@ukma.edu.ua", authorities = "TEACHER")
    void shouldReturnForbidden_whenTeacherTriesToAccessCourse() throws Exception {
        mockMvc.perform(post("/course/add").param("courseName", "bad").param("grNum", "-1"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(username = "student@ukma.edu.ua", authorities = "STUDENT")
    void shouldReturnForbidden_whenStudentTriesToAccessCourse() throws Exception {
        mockMvc.perform(get("/course")).andExpect(status().is(403));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirect_whenAccessCourseUnauthorized() throws Exception {
        mockMvc.perform(get("/course")).andExpect(status().is(302));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirect_whenAccessAddCourseUnauthorized() throws Exception {
        mockMvc.perform(get("/course/add")).andExpect(status().is(302));
    }

    @Test
    @WithAnonymousUser
    void shouldRedirect_whenAccessDeleteCourseUnauthorized() throws Exception {
        mockMvc.perform(get("/course/delete")).andExpect(status().is(302));
    }

}
