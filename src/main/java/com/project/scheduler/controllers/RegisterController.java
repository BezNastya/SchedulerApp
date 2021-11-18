package com.project.scheduler.controllers;

import com.project.scheduler.entity.User;
import com.project.scheduler.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//@Controller
public class RegisterController {
    /*
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            return "register";
        }

        //For testing purposes set user`s role to STUDENT and authorize them
        userForm.setRole("STUDENT");
        userForm.setAuthorized(true);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        userService.save(userForm);
        //userService.login(userForm.getEmail(), userForm.getPassword());

        return "redirect:/login";
    }*/
}
