package com.project.scheduler.controllers;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import com.project.scheduler.entity.User;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.StudentService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class GroupController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final UserService userService;

    public GroupController(StudentService studentService, CourseService courseService, UserService userService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/new-groups")
    public String addGroupCourse(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<Course> courses = courseService.findNotAttendedCourses(user.getUserId());
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        model.addAttribute("courseService", courseService);
        Course course = new Course();
        model.addAttribute("curr", course);
        return "addGroup";
    }

    @GetMapping("/new-groups/add")
    public String addGroup(Principal principal, @RequestParam(name="inputSelect") int group, @ModelAttribute("course") Course course) {
        Student student = studentService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        studentService.addGroupForUser(student.getUserId(), course, (byte) group);
        return "redirect:/new-groups";
    }

//    @GetMapping("/add-group-course")
//    public Set<GroupCourse> addGroupCourse() {
//        return studentService.addGroupForUser(2L, courseService.findCourseByName("Test1").get(), (byte)5).getGroupCourse();
//    }

    // TODO Add the summary
    @Operation(summary = "")
    @GetMapping("/my-groups")
    public String viewGroups(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<GroupCourse> groupCourseList = courseService.findGroupCoursesByEducationUserId(user.getUserId());
        model.addAttribute("groupCourseList", groupCourseList);
        model.addAttribute("user", user);
        return "myGroups";
    }

    // TODO Add the summary
    //TODO Change to delete mapping
    @Operation(summary = "")
    @GetMapping("/my-groups/delete-group")
    public String deleteGroupCourse(Principal principal, @RequestParam Long id){
        Student student = studentService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        studentService.deleteGroupForUserByGroupCourse(
                student.getUserId(),
                courseService.findGroupById(id));
        return "redirect:/my-groups";
    }
}
