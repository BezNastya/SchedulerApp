package com.project.scheduler.controllers.views;

import com.project.scheduler.dto.CourseDTO;
import com.project.scheduler.dto.GroupDTO;
import com.project.scheduler.entity.*;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.service.CourseService;
import com.project.scheduler.service.StudentService;
import com.project.scheduler.service.TeacherService;
import com.project.scheduler.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class GroupController {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(GroupController.class);

    public GroupController(StudentService studentService, TeacherService teacherService, CourseService courseService, UserService userService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/new-groups")
    public String addGroupCourse(Principal principal,
                                 Model model,
                                 @RequestParam(value = "courseId", required = false) Long courseId) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<CourseDTO> courses = courseService.findNotAttendedCourses(user.getUserId());
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        model.addAttribute("courseId", courseId);
        List<GroupDTO> groupCourses = null;
        if (courseId != null) {
            groupCourses = courseService.findAllGroupsForCourse(courseId);
        }
        model.addAttribute("groupCourses", groupCourses);
        return "addGroup";
    }

    @GetMapping("/new-groups/add")
    public String addGroup(Principal principal,
                           @RequestParam(name="inputSelect") long group,
                           @RequestParam(name="courseId") Long courseId) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        logger.warn("Adding group {} for course {}", group, courseId);
        GroupCourse groupCourse = courseService.findGroupById(group);
        if (user.getRole() == Role.STUDENT)
            studentService.addGroupForUser(
                    user.getUserId(),
                    groupCourse);
        else if (user.getRole() == Role.TEACHER)
            teacherService.addGroupForUser(
                    user.getUserId(),
                    groupCourse);
        return "redirect:/new-groups";
    }

    @Operation(summary = "View my groups")
    @GetMapping("/my-groups")
    public String viewGroups(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        List<Long> groupCourseIds = courseService.findGroupCoursesByEducationUserId(user.getUserId())
                        .stream().map(GroupCourse::getId).collect(Collectors.toList());
        List<GroupDTO> groupCourseList = courseService.findAllGroupsFotGroupsIds(groupCourseIds);
        model.addAttribute("groupCourseList", groupCourseList);
        model.addAttribute("user", user);
        return "myGroups";
    }

    @Operation(summary = "Leave group")
    @GetMapping("/my-groups/delete-group")
    public String deleteGroupCourse(Principal principal, @RequestParam Long id){
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new UserNotFoundException(principal.getName()));
        GroupCourse groupCourse = courseService.findGroupById(id);
        if (user.getRole() == Role.STUDENT)
            studentService.deleteGroupForUserByGroupCourse(
                    user.getUserId(), groupCourse);
        else if (user.getRole() == Role.TEACHER)
            teacherService.deleteGroupForUserByGroupCourse(
                    user.getUserId(), groupCourse);
        return "redirect:/my-groups";
    }
}
