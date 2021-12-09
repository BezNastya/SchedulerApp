package com.project.scheduler.controllers;

import com.project.scheduler.entity.*;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.repository.LessonRepository;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AdminScheduleController {

    private final CourseService courseService;
    private final UserService userService;
    private final LessonRepository lessonRepository;

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
                            @RequestParam("week") int week,
                            @RequestParam("place") String place,
                            @RequestParam("type") LessonType type,
                            @RequestParam("grNum") GroupCourse grNum) {
        Lesson lesson = new Lesson();
        lesson.setDate(new ScheduleDate(day, lessonOrder, week));
        lesson.setPlace(place);
        lesson.setType(type);
        lesson.setGroupCourse(grNum);
        lessonRepository.save(lesson);
        return "redirect:/admin-lessons";
    }

    @GetMapping("/admin-lessons/edit")
    public String editCourse(Model model){

        model.addAttribute("lesson",new Lesson());

        return "redirect:/admin-lessons";
    }

    @PostMapping("/admin-lessons/edit")
    public String editCourse(@RequestParam("id") Long id,
                             @RequestParam("day") WeekDay day,
                             @RequestParam("order") LessonOrder lessonOrder,
                             @RequestParam("week") int week,
                             @RequestParam("place") String place,
                             @RequestParam("type") LessonType type,
                             @RequestParam("grNum") GroupCourse groupCourse,
                             @ModelAttribute("postponeLesson")Lesson lesson){

        lesson.setDate(new ScheduleDate(day, lessonOrder, week));
        lesson.setPlace(place);
        lesson.setType(type);
        lesson.setGroupCourse(groupCourse);
        lessonRepository.save(lesson);
        return "/admin-lessons";
    }

    //TODO DeleteMapping
    @GetMapping("/admin-lessons/delete")
    public String deleteCourse(@RequestParam("id") Long id){
        lessonRepository.deleteById(id);
        return "redirect:/admin-lessons";
    }
}
