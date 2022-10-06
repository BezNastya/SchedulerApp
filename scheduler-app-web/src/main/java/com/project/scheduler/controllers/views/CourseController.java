package com.project.scheduler.controllers.views;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.User;
import com.project.scheduler.exceptions.CourseNotFoundException;
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
import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService){
        this.courseService = courseService;
        this.userService = userService;
    }

    @Operation(summary = "New course view")
    @GetMapping("/course")
    public String course(Model model, Principal principal){
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("user", userService.findByEmail(principal.getName()).get());
        return "course";
    }

    @Operation(summary = "Add new course")
    @PostMapping("/course/add")
    public String addCourse(@RequestParam("courseName") String courseName,
                            @RequestParam("grNum") String grNum) {
        if (courseService.findCourseByName(courseName).isEmpty()) {
            Course course = new Course();
            course.setName(courseName);
            courseService.saveCourse(course);
            courseService.saveGroupsForCourse(course, (byte) Integer.parseInt(grNum));
            logger.info("Added course with name {}", courseName);
        }
        return "redirect:/course";
    }

    @Operation(summary = "Add lessons to course")
    @GetMapping("/course/addLessons")
    public ModelAndView addLessons(@RequestParam("id") Long id, Principal principal, ModelMap model) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        model.addAttribute("user", user);
        Course course = courseService.findCourseById(id).orElseThrow(() -> new CourseNotFoundException(id));
        model.addAttribute("groups", courseService.findAllGroupsForCourse(course));
        model.addAttribute("place", "");
        return new ModelAndView("courseLessonsForm", model);
    }

    @Operation(summary = "Delete the specified course")
    @GetMapping("/course/delete")
    public String deleteCourse(@RequestParam("id") Long id) {
        logger.info("Deleting course with id{}", id);
        Optional<Course> courseOptional = courseService.findCourseById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            courseService.deleteCourseWithAll(course);
        }
        return "redirect:/course";
    }


}
