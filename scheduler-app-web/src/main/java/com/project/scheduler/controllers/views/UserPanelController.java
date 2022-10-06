package com.project.scheduler.controllers.views;

import com.project.scheduler.entity.User;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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


    @Operation(summary = "Show the information about the user")
    @GetMapping("/user")
    public String userPanel(Principal principal, Model model){
//        if (principal == null) return "login";

        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("user", user);
        return "user";
    }
}
