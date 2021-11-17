package com.project.scheduler.controllers;


import com.project.scheduler.entity.Admin;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    Marker myMarker = MarkerFactory.getMarker("AdminClassMarker");

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/user")
    public String userPanel(Principal principal, Model model){
//        Optional<Admin> user = adminService.findByEmail(principal.getName());
          Admin user = new Admin();
//        if (user != null) {
            model.addAttribute("user", user);
//        }
//        else {
//            return "error/404";
//        }

        return "user";
    }

//    public long getUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getPrincipal();
//        return adminService.findByEmail(username).get().getUserId();
//    }


//    @GetMapping("/admin/{id}")
//    public String clientMCDRequest(@PathVariable String id) throws InterruptedException {
//        MDC.put("adminId", id);
//
//        logger.info(myMarker, "admins {} has made a request", id);
//        logger.info(myMarker, "Starting request");
//        Thread.sleep(5000);
//        logger.info(myMarker, "Finished request");
//        MDC.clear();
//        return "finished";
//    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        logger.info(myMarker, "Getting all admins");
        return adminService.findAll();
    }

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
        Admin admin = adminService.findById(id).get();
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
