package com.project.scheduler.controllers;

import com.project.scheduler.dto.PostponeLessonDto;
import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.User;
import com.project.scheduler.service.PostponeLessonService;
import com.project.scheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/requests")
public class PostponeViewController {

    @Autowired
    private PostponeLessonService postponeLessonService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllPostponeRequests(Principal principal, Model model){
        List<PostponeLesson> requestsP = postponeLessonService.getAllRequests();

        List<PostponeLessonDto> requests = requestsP
                .stream()
                .map(PostponeLessonDto::new)
                .collect(Collectors.toList());
        User user = userService.findByEmail(principal.getName()).get();

        model.addAttribute("requests", requests);
        model.addAttribute("user", user);
        return "postponeTable";
    }

    @GetMapping("/approve")
    public String approveRequest(@RequestParam Long id){
        if (postponeLessonService.approveRequest(id))
            return "redirect:/requests";
        return "errorReq";
    }

    @GetMapping("/decline")
    public String declineRequest(@RequestParam Long id){
        if (postponeLessonService.declineRequest(id))
            return "redirect:/requests";
        return "errorReq";
    }

    @GetMapping("/delete")
    public String deleteRequest(@RequestParam Long id){
        postponeLessonService.deleteRequest(id);
        return "redirect:/requests";
    }
}
