package com.project.scheduler.controller;


import com.project.scheduler.entity.Admin;
import com.project.scheduler.service.AdminService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    Marker myMarker = MarkerFactory.getMarker("AdminClassMarker");
    @Autowired
    AdminService adminService;


    @GetMapping("/admin/{id}")
    public String clientMCDRequest(@PathVariable String id) throws InterruptedException {
        MDC.put("adminId", id);

        logger.info(myMarker, "admins {} has made a request", id);
        logger.info(myMarker, "Starting request");
        Thread.sleep(5000);
        logger.info(myMarker, "Finished request");
        MDC.clear();
        return "finished";
    }

    @GetMapping("/admin")
    public List<Admin> getAdmins() {
        return adminService.findAll();
    }

    @PostMapping("/admin/add")
    public String addAdmin(@RequestBody Admin adminBody){
        Admin admin = new Admin();
        admin.setEmail(adminBody.getEmail());
        admin.setPassword(adminBody.getPassword());
        admin.setFirstName(adminBody.getFirstName());
        admin.setLastName(adminBody.getLastName());
        admin.setRole("admin");
        admin.setAuthorized(true);

        adminService.save(admin);
        return" new admin added";
    }


    @PutMapping("/admin/edit/{id}")
    public String editAdmin(@PathVariable long id) {
        Admin admin = adminService.findById(id);
        admin.setFirstName("Admin1");

        adminService.update(admin);
        return"  student updated";
    }



    @DeleteMapping("/admin/delete/{id}")
    public String deleteStudent(@PathVariable long id) {
        Admin admin = adminService.findById(id);

        adminService.delete(admin);
        return" new student deleted";
    }

/*    자신의 비밀번호를 갱신한 뒤 그 결과를 반환
    @PutMapping(value = "/me")
    public User updatePassword(@RequestAttribute User user, @RequestParam String password) {
        return userService.updatePassword(user.getId(), password);
    }

    // 탈퇴
    @DeleteMapping(value = "/me")
    public void withdraw(@RequestAttribute User user) {
        userService.withdraw(user.getId());
    }
 */
}
