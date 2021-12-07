package com.project.scheduler.controllers;

import com.project.scheduler.entity.User;
import com.project.scheduler.service.HomeService;
import com.project.scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String homePage(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName()).get();
        model.addAttribute("user", user);
        return "home";
    }
}
