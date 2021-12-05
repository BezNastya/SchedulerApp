package com.project.scheduler.controllers;

import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        Student student = studentService.findByEmail(principal.getName()).get();
        List<GroupCourse> groupCourseList = courseService.findGroupCoursesByEducationUserId(student.getUserId());

        model.addAttribute("groupCourseList", groupCourseList);
        return "addGroup";
    }

//    @GetMapping("/add-group-course")
//    public Set<GroupCourse> addGroupCourse() {
//        return studentService.addGroupForUser(2L, courseService.findCourseByName("Test1").get(), (byte)5).getGroupCourse();
//    }

    @GetMapping("/view-my-groups")
    public String viewGroups(Principal principal, Model model) {
        Student student = studentService.findByEmail(principal.getName()).get();
        List<GroupCourse> groupCourseList = courseService.findGroupCoursesByEducationUserId(student.getUserId());
        model.addAttribute("groupCourseList", groupCourseList);
        return "myGroups";
    }

    @GetMapping("/delete-group-course")
    public String deleteGroupCourse(Principal principal, @RequestParam Long id){
        Student student = studentService.findByEmail(principal.getName()).get();
        studentService.deleteGroupForUserByGroupCourse(
                student.getUserId(),
                courseService.findGroupById(id));
        return "redirect:/view-my-groups";
    }
}
