package com.project.scheduler.controllers;


import com.project.scheduler.entity.Admin;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.AdminService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
/*
    @GetMapping("/{email}")
    public Admin getAdminByEmail(@PathVariable String email) {
        logger.info(myMarker, "Getting admin with email {}", email);
        return adminService.findByEmail(email).orElseThrow(new );
    }
*/
    @PostMapping("/add")
    public Admin addAdmin(@RequestBody @Valid Admin admin){
        logger.info(myMarker, "Adding admin {}", admin);
        return adminService.save(admin);
    }
/*
    @PutMapping("/{id}/firstName")
    public void editAdminFirstName(@PathVariable long id, @RequestParam String newName) {
        Admin admin = adminService.findById(id);
        admin.setFirstName(newName);
        logger.info(myMarker, "Updating admin {} first name to {}", newName, id);
        adminService.update(admin);
    }
*/
    @PutMapping("/{id}/lastName")
    public void editAdminLastName(@PathVariable long id, @RequestParam String newName) {
        Admin admin = adminService.findById(id).get();
        admin.setLastName(newName);
        logger.info(myMarker, "Updating admin {} last name to {}", newName, id);
        adminService.update(admin);
    }

    @DeleteMapping("/remove/{id}")
    public void deleteAdmin(@PathVariable long id) {
        logger.info(myMarker, "Deleting admin with id{}", id);
        adminService.delete(adminService.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

}
