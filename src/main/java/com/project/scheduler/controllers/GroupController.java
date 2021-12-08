package com.project.scheduler.controllers;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class GroupController {
    private final StudentService studentService;
    private final CourseService courseService;

    public GroupController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/view-new-groups")
    public String addGroupCourse(Principal principal, Model model) {
        Student student = studentService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
//        List<GroupCourse> groupCourseList = courseService.findGroupCoursesByEducationUserId(student.getUserId());
        List<Course> courses = courseService.findNotAttendedCourses(student.getUserId());
        model.addAttribute("courses", courses);
        model.addAttribute("courseService", courseService);
        return "addGroup";
    }

    @GetMapping("/add-group")
    public String addGroup(Principal principal, @RequestParam(name="inputSelect") int group, @ModelAttribute("course") Course course) {
        Student student = studentService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        studentService.addGroupForUser(student.getUserId(), course, (byte) group);
        return "redirect:/view-new-groups";
    }

//    @GetMapping("/add-group-course")
//    public Set<GroupCourse> addGroupCourse() {
//        return studentService.addGroupForUser(2L, courseService.findCourseByName("Test1").get(), (byte)5).getGroupCourse();
//    }

    // TODO Add the summary
    @Operation(summary = "")
    @GetMapping("/view-my-groups")
    public String viewGroups(Principal principal, Model model) {
        Student student = studentService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<GroupCourse> groupCourseList = courseService.findGroupCoursesByEducationUserId(student.getUserId());
        model.addAttribute("groupCourseList", groupCourseList);
        return "myGroups";
    }

    // TODO Add the summary
    @Operation(summary = "")
    @GetMapping("/delete-group-course")
    public String deleteGroupCourse(Principal principal, @RequestParam Long id){
        Student student = studentService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        studentService.deleteGroupForUserByGroupCourse(
                student.getUserId(),
                courseService.findGroupById(id));
        return "redirect:/view-my-groups";
    }
}
