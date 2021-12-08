package com.project.scheduler.controllers;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    Marker myMarker = MarkerFactory.getMarker("AdminClassMarker");

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //TODO Do we need this method?
    @GetMapping
    public List<Admin> getAllAdmins() {
        logger.info(myMarker, "Getting all admins");
        return adminService.findAll();
    }

    //TODO Do we need this method?
    //@PreAuthorize("#id ")
    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable Long id) {
//        MDC.put("adminId", String.valueOf(id));
        logger.info(myMarker, "Getting admin with id {}", id);
        return adminService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }


    @Operation(summary = "Add a new admin")
    @PostMapping("/add")
    public Admin addAdmin(@RequestBody @Valid Admin admin){
        logger.info(myMarker, "Adding admin {}", admin);
        return adminService.save(admin);
    }

    @Operation(summary = "Change the last name of the specified admin")
    @PutMapping("/{id}/lastName")
    public void editAdminLastName(@PathVariable long id, @RequestParam String newName) {
        Admin admin = adminService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        admin.setLastName(newName);
        logger.info(myMarker, "Updating admin {} last name to {}", newName, id);
        adminService.update(admin);
    }

    @Operation(summary = "Delete the admin with the specified id")
    @DeleteMapping("/remove/{id}")
    public void deleteAdmin(@PathVariable long id) {
        logger.info(myMarker, "Deleting admin with id{}", id);
        adminService.delete(adminService.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

}
