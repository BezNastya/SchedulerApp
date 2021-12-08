package com.project.scheduler.controllers;

import com.project.scheduler.entity.PostponeLesson;
import com.project.scheduler.entity.ScheduleDate;
import com.project.scheduler.entity.User;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.PostponeLessonService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PostponeLessonController {

    Logger logger = LoggerFactory.getLogger(PostponeLessonController.class);

    @Autowired
    private PostponeLessonService postponeLessonService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Show the teacher all of their lessons and allow them to create a postpone-request")
    @GetMapping("/postponeLesson")
    public String postponeLessonForm(Principal principal, Model model,
                                     @RequestParam(name = "week", required = false, defaultValue = "1") int week) {

        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("lessons", courseService.findLessonsByEducationUserIdForWeek(user.getUserId(), week));
        return "postponeLesson";
    }


    //@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date   - for requesting dates
    @Operation(summary = "Add a new postpone request")
    @PostMapping("/postponeLesson")
    public String submitPostponeLessonForm(@RequestParam("id") long id, @RequestParam("week") int week,
                                           @RequestParam("day") int day, @RequestParam("lesson") int lesson,
                                           @ModelAttribute("postponeLesson")PostponeLesson postponeLesson) {

        logger.warn("Received submit request with id {}, week {}, day {}, lesson order {} ", id, day, week, lesson);
        ScheduleDate scheduleDate = new ScheduleDate();
        scheduleDate.setWeek(week);
        //scheduleDate.setDayOfTheWeek(day);
//        scheduleDate.setLessonOrder(lesson);
        postponeLesson.setNewDate(scheduleDate);
        postponeLessonService.postponeLesson(id, postponeLesson);
        return "/postponeLesson";
    }

}