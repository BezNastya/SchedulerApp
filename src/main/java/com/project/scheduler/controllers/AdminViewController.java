package com.project.scheduler.controllers;

import com.project.scheduler.dto.PostponeLessonDto;
import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.service.PostponeLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/postpone")
public class AdminViewController {

    @Autowired
    private PostponeLessonService postponeLessonService;

    @GetMapping
    public String getAllPostponeRequests(Model model){
        List<PostponeLesson> requestsP = postponeLessonService.getAllRequests();

        List<PostponeLessonDto> requests = requestsP
                .stream()
                .map(PostponeLessonDto::new)
                .collect(Collectors.toList());

        model.addAttribute("requests", requests);
        return "postpone-admin";
    }
}
