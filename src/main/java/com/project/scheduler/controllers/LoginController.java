package com.project.scheduler.controllers;

import com.project.scheduler.entity.User;
import com.project.scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(Model model, String error){
        //No connection with the database!
        if (error != null && !error.isEmpty())
            model.addAttribute("error", "Your username and password are invalid.");
        return "login";
    }

}
