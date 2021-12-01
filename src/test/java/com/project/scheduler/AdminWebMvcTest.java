package com.project.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.scheduler.controllers.AdminController;
import com.project.scheduler.entity.Admin;
import com.project.scheduler.security.UserDetailsServiceImpl;
import com.project.scheduler.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminController.class)
public class AdminWebMvcTest {

    @MockBean
    AdminService adminService;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    MockMvc mvc;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static final List<Admin> ADMINS = Collections.singletonList(new Admin("admin@ukma.edu.ua", encoder.encode("admin"), "Admin`s first name", "Admin`s last name"));
    private static final Admin TO_ADD_VALID = new Admin("email@e.com", "password", "First", "Last");
    private static final Admin TO_ADD_INVALID = new Admin("email", "password", "First", "Last");

    @Test
    void shouldRedirectToLoginPage() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin@ukma.edu.ua", password = "admin", authorities = "ADMIN")
    @Test
    void shouldReturnAllAdmins() throws Exception {
        when(adminService.findAll()).thenReturn(ADMINS);
        mvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(ADMINS.get(0)));
    }

    @WithMockUser(username = "admin@ukma.edu.ua", password = "admin", authorities = "ADMIN")
    @Test
    void shouldReturnExistingAdmin() throws Exception {
        when(adminService.findById((long) 1)).thenReturn(Optional.of(ADMINS.get(0)));
        mvc.perform(get("/admin/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ADMINS.get(0)));
    }

    @WithMockUser(username = "admin@ukma.edu.ua", password = "admin", authorities = "ADMIN")
    @Test
    void shouldReturnValidationError_whenNonExistingAdmin() throws Exception {
        String validationOutput = "There is no user with id 1";
        when(adminService.findById((long) 1)).thenReturn(Optional.empty());
        mvc.perform(get("/admin/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(validationOutput));
    }

    @WithMockUser(username = "admin@ukma.edu.ua", password = "admin", authorities = "ADMIN")
    @Test
    void shouldAddSuccessfully_whenValidAdmin() throws Exception {
        when(adminService.save(any())).thenReturn(TO_ADD_VALID);
        mvc.perform(post("/admin/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TO_ADD_VALID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(TO_ADD_VALID));
    }

    @WithMockUser(username = "admin@ukma.edu.ua", password = "admin", authorities = "ADMIN")
    @Test
    void shouldReturnValidationError_whenInvalidAdmin() throws Exception {
        when(adminService.save(any())).thenReturn(TO_ADD_INVALID);
        mvc.perform(post("/admin/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TO_ADD_INVALID)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty());
    }

}
