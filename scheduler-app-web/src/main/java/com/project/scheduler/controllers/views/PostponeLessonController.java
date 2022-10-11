package com.project.scheduler.controllers.views;

import com.project.scheduler.dto.PostponeLessonRequestDTO;
import com.project.scheduler.dto.PostponeLessonResponseDTO;
import com.project.scheduler.entity.*;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.PostponeLessonService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class PostponeLessonController {

    Logger logger = LoggerFactory.getLogger(PostponeLessonController.class);

    private final PostponeLessonService postponeLessonService;
    private final UserService userService;

    @Autowired
    public PostponeLessonController(PostponeLessonService postponeLessonService, UserService userService) {
        this.postponeLessonService = postponeLessonService;
        this.userService = userService;
    }

    @PostMapping("/postponeLessonSubmit/{id}")
    public String postponeLesson(@PathVariable("id") long id, @RequestParam("week") int week, @RequestParam("day") String day,
                                 @RequestParam("order") String order, @RequestParam("description") String description,
                                 Principal principal, ModelMap model) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("user", user);
        logger.warn("Received submit request for a lesson with:\n Id: {},\n Week: {},\n Day: {},\n Order: {},\n Description: {}", id, week, day, order, description);
        PostponeLessonRequestDTO postponeLesson = PostponeLessonRequestDTO.builder()
                        .lessonId(id).week(week)
                        .day(WeekDay.fromString(day)).order(LessonOrder.fromString(order)).description(description)
                        .teachers(user.getFirstName() + user.getLastName())
                        .build();
        postponeLessonService.postponeLesson(postponeLesson);
        return "redirect:/my-lessons";
    }

    @GetMapping("/postponeLessonReview")
    public ModelAndView changePostponeLesson(@RequestParam("id") long id, Principal principal, ModelMap model) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("user", user);
        PostponeLessonResponseDTO postpone = postponeLessonService.getRequestByLessonId(id);
        model.addAttribute("postpone", postpone);
        return new ModelAndView("postponeLesson", model);
    }

    @Operation(summary = "Add a new postpone request")
    @GetMapping("/postponeLessonForm")
    public ModelAndView chooseLessonToPostpone(@RequestParam("id") long id, Principal principal, ModelMap model) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("user", user);
        PostponeLessonResponseDTO postpone = postponeLessonService.getRequestByLessonId(id);
        model.addAttribute("postpone", postpone);
        logger.warn("Received submit request for a lesson with id {}", id);
        return new ModelAndView("postponeLesson", model);
    }

}