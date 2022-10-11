package com.project.scheduler.controllers.views;

import com.project.scheduler.dto.PostponeLessonResponseDTO;
import com.project.scheduler.entity.User;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.PostponeLessonService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/requests")
public class PostponeViewController {

    @Autowired
    private PostponeLessonService postponeLessonService;
    @Autowired
    private UserService userService;

    @Operation(summary = "Show all postpone requests")
    @GetMapping
    public String getAllPostponeRequests(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<PostponeLessonResponseDTO> requests = postponeLessonService.getAllRequestsForUser(user);
        model.addAttribute("requests", requests);
        model.addAttribute("user", user);
        return "postponeTable";
    }

    @Operation(summary = "Approve the selected request")
    @PostMapping("/approve")
    public String approveRequest(@RequestParam Long id){
        if (postponeLessonService.approveRequest(id))
            return "redirect:/requests";
        return "errorReq";
    }

    @Operation(summary = "Decline the selected request")
    @PostMapping("/decline")
    public String declineRequest(@RequestParam Long id){
        if (postponeLessonService.declineRequest(id))
            return "redirect:/requests";
        return "errorReq";
    }

    @Operation(summary = "Remove the selected request")
    @GetMapping("/delete")
    public String deleteRequest(@RequestParam Long id){
        postponeLessonService.deleteRequest(id);
        return "redirect:/requests";
    }
}
