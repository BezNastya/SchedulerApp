package com.project.scheduler.controllers;

import com.project.scheduler.entity.*;
import com.project.scheduler.exceptions.CourseNotFoundException;
import com.project.scheduler.exceptions.LessonNotFoundException;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.LessonRepository;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class AdminScheduleController {

    private final CourseService courseService;
    private final UserService userService;
    private final LessonRepository lessonRepository;
    Logger logger = LoggerFactory.getLogger(PostponeLessonController.class);


    @Autowired
    public AdminScheduleController(CourseService courseService, UserService userService, LessonRepository lessonRepository) {
        this.courseService = courseService;
        this.userService = userService;
        this.lessonRepository = lessonRepository;
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
    public String addLesson(@RequestParam("day") WeekDay day,
                            @RequestParam("order") LessonOrder lessonOrder,
                            @RequestParam("week1") int weekStart,
                            @RequestParam("week2") int weekEnd,
                            @RequestParam("place") String place,
                            @RequestParam("type") LessonType type,
                            @RequestParam("group") Long group) {
        GroupCourse groupCourse = courseService.findGroupById(group);
        if (weekStart <= 0 || weekStart > weekEnd || weekEnd > 15) {
            throw new RuntimeException("Invalid week range set for lessons");
        }
        for (int i = weekStart; i <= weekEnd; i++) {
            ScheduleDate date = new ScheduleDate(day, lessonOrder, i);
            Lesson lesson = new Lesson(type, place, date, groupCourse);
            lessonRepository.save(lesson);
            logger.warn("New lesson added {}", lesson);
        }
        return "redirect:/admin-lessons";
    }

    @GetMapping("/admin-lessons/edit")
    public ModelAndView editCourse(@RequestParam("id") Long id, Principal principal, ModelMap model){
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("user", user);
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new LessonNotFoundException(id));
        model.addAttribute("lesson", lesson);
        return new ModelAndView("courseLessonsEditForm", model);
    }

    @PostMapping("/admin-lessons/edit")
    public String editCourse(@RequestParam("lesson") Long id,
                             @RequestParam("day") WeekDay day,
                             @RequestParam("order") LessonOrder lessonOrder,
                             @RequestParam("week1") int weekStart,
                             @RequestParam("week2") int weekEnd,
                             @RequestParam("place") String place,
                             @RequestParam("type") LessonType type,
                             @RequestParam("group") Long groupId){
        GroupCourse group = courseService.findGroupById(groupId);
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new LessonNotFoundException(id));
        List<Lesson> lessonsToEdit = courseService.findLessonsByGroupCourse(group);
        for (Lesson lesson1 : lessonsToEdit) {
            boolean theSameDate = lesson1.getDate().getLessonOrder().getOrder().equals(lesson.getDate().getLessonOrder().getOrder())
                    && lesson1.getDate().getDayOfTheWeek().getDay().equals(lesson.getDate().getDayOfTheWeek().getDay());
            if (theSameDate)
                lessonRepository.deleteById(lesson1.getLessonId());
        }
        if (weekStart <= 0 || weekStart > weekEnd || weekEnd > 15) {
            throw new RuntimeException("Invalid week range set for lessons");
        }
        for (int i = weekStart; i <= weekEnd; i++) {
            ScheduleDate date = new ScheduleDate(day, lessonOrder, i);
            Lesson lesson2 = new Lesson(type, place, date, group);
            lessonRepository.save(lesson2);
        }
        return "redirect:/admin-lessons";
    }

    //TODO DeleteMapping
    @GetMapping("/admin-lessons/delete")
    public String deleteCourse(@RequestParam("id") Long id){
        lessonRepository.deleteById(id);
        return "redirect:/admin-lessons";
    }
}
