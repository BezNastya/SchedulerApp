package com.project.scheduler.controllers.views;

import com.project.scheduler.dto.LessonDTO;
import com.project.scheduler.dto.LessonRequestDTO;
import com.project.scheduler.entity.*;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class AdminScheduleController {

    private final CourseService courseService;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(PostponeLessonController.class);


    @Autowired
    public AdminScheduleController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Operation(summary = "Get all lessons")
    @GetMapping("/admin-lessons")
    public String findLessonsByUser(Principal principal, Model model, @RequestParam(name = "inputSelect", required = false, defaultValue = "1") Integer week) {
        User user=userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        week = week < 1 ? 1 : week;
        model.addAttribute("lessonsByWeek",
                courseService.findAllLessonsByWeek(week));
        model.addAttribute("week", week);
        model.addAttribute("user",user);
        return "adminschedule";
    }

    @PostMapping("/admin-lessons/add")
    public String addLesson(@RequestParam("day") String day,
                            @RequestParam("order") String lessonOrder,
                            @RequestParam("week1") int weekStart,
                            @RequestParam("week2") int weekEnd,
                            @RequestParam("place") String place,
                            @RequestParam("type") String type,
                            @RequestParam("group") Long group) {
        LessonRequestDTO requestDTO = LessonRequestDTO.builder()
                        .groupId(group)
                        .time(LessonOrder.fromString(lessonOrder))
                        .day(WeekDay.fromString(day)).weekStart(weekStart)
                        .weekEnd(weekEnd).place(place)
                        .type(LessonType.fromString(type)).build();
        if (weekStart < UserScheduleController.FIRST_WEEK || weekStart > weekEnd || weekEnd > UserScheduleController.LAST_WEEK) {
            throw new RuntimeException("Invalid week range set for lessons");
        }
        courseService.addLessons(requestDTO);
        return "redirect:/admin-lessons";
    }

    @GetMapping("/admin-lessons/edit")
    public ModelAndView editCourse(@RequestParam("id") Long id, Principal principal, ModelMap model){
        logger.warn("Editing course with id {}", id);
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("user", user);
        LessonDTO lesson = courseService.findLessonById(id);
        model.addAttribute("lesson", lesson);
        return new ModelAndView("courseLessonsEditForm", model);
    }

    @PostMapping("/admin-lessons/edit")
    public String editCourse(@RequestParam("lesson") Long id,
                             @RequestParam("day") String day,
                             @RequestParam("order") String lessonOrder,
                             @RequestParam("week1") int weekStart,
                             @RequestParam("week2") int weekEnd,
                             @RequestParam("place") String place,
                             @RequestParam("type") String type,
                             @RequestParam("group") Long groupId){
        LessonRequestDTO requestDTO = LessonRequestDTO.builder()
                .id(id)
                .groupId(groupId)
                .time(LessonOrder.fromString(lessonOrder))
                .day(WeekDay.fromString(day)).weekStart(weekStart)
                .weekEnd(weekEnd).place(place)
                .type(LessonType.fromString(type)).build();
        if (weekStart < UserScheduleController.FIRST_WEEK || weekStart > weekEnd || weekEnd > UserScheduleController.LAST_WEEK) {
            throw new RuntimeException("Invalid week range set for lessons");
        }
        courseService.editLessons(requestDTO);
        return "redirect:/admin-lessons";
    }

    @GetMapping("/admin-lessons/delete")
    public String deleteCourse(@RequestParam("id") Long id){
        logger.warn("Removing course with id {}", id);
        courseService.deleteLessonById(id);
        return "redirect:/admin-lessons";
    }
}
