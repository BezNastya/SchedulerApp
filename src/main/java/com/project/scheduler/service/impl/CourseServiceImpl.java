package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import com.project.scheduler.exceptions.CourseNotFoundException;
import com.project.scheduler.repository.CourseRepository;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;
    GroupCourseRepository groupRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, GroupCourseRepository groupCourseRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupCourseRepository;
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourseById(Long courseId) {
courseRepository.deleteById(courseId);
    }

    @Override
    public void updateCourseName(String newName, Long toUpdateId) {
        Course courseToUpdate = findCourseById(toUpdateId)
                .orElseThrow(() -> new CourseNotFoundException(toUpdateId));
        courseToUpdate.setName(newName);
        courseRepository.save(courseToUpdate);
    }

    @Override
    public Optional<Course> findCourseById(long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Set<GroupCourse> findAllGroupsForCourse(Course course) {
        return groupRepository.findAllGroupsCourseByCourse(course)
                .stream()
                .collect(Collectors.toSet());
    }

    @Override
    public GroupCourse findGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }


    @Override
    public Course saveGroupsForCourse(Course course, byte numberOfGroups) {
        Set<GroupCourse> res = new HashSet<>();
        for (byte i = 1; i <= numberOfGroups; i++){
            GroupCourse group = new GroupCourse(i);
            group.setCourse(course);
            group.setStudents(new HashSet<>());
            group.setTeachers(new HashSet<>());
            res.add(group);
            groupRepository.save(group);
        }
        course.setGroups(res);
        return courseRepository.save(course);
    }

    @Override
    public void deleteGroupById(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public void deleteAllGroups(Course course) {
        Set<GroupCourse> groups = findAllGroupsForCourse(course);
        for (GroupCourse group : groups){
            deleteGroupById(group.getId());
        }
    }

    @Override
    public List<GroupCourse> findAllByStudents(Long id) {
        return groupRepository.findAllByStudents(id);
    }
}
