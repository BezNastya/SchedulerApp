package com.project.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.scheduler.entity.Admin;
import com.project.scheduler.service.AdminService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AdminWebMvcTest {

    @Mock
    AdminService adminService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mvc;

    private static final List<Admin> ADMINS = Arrays.asList(new Admin("email@e.com", "111", "Admin", "Admin"),
            new Admin("email2@e.com", "222", "First", "Last"),
            new Admin("email3@e.com", "333", "Fff", "Bae"));


    @Test
    void shouldReturnAllAdmins() throws Exception {
        when(adminService.findAll()).thenReturn(ADMINS);
        mvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection());
    }

}
