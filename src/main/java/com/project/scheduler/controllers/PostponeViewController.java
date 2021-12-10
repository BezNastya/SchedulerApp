package com.project.scheduler.controllers;

import com.project.scheduler.dto.PostponeLessonDto;
import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.Role;
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
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

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
        List<PostponeLesson> requestsP = postponeLessonService.getAllRequests();
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));

        List<PostponeLessonDto> requests = requestsP
                .stream()
                .filter(x -> {
                    if (user.getRole().equals(Role.TEACHER) &&
                            !x.getCanceledLesson().getGroupCourse().getTeachers().contains(user)){
                        return false;
                    }
                    return true;
                })
                .map(PostponeLessonDto::new)
                .collect(Collectors.toList());
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
