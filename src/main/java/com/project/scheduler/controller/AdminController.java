package com.project.scheduler.controller;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String userPanel(Principal principal, Model model){
        Admin admin = adminService.findByEmail(principal.getName());// TODO fix

        if (admin != null) {
            model.addAttribute("admin", admin);
        } else {
            return "error/404";
        }

        return "user";
    }
}
