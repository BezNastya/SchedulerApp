package com.project.scheduler.controllers;

import com.project.scheduler.entity.User;
import com.project.scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
@Controller
public class UserPanelController {

    private final UserService userService;
    @Autowired
    public UserPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userPanel(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName()).get();

        model.addAttribute("user", user);
        return "user";
    }
}
