package com.project.scheduler.service;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    List<Course> findAllByStudent(Student student);
    List<GroupCourse> findAllGroups();
    GroupCourse findGroupById(Long id);
    GroupCourse saveGroup(GroupCourse group);
    boolean updateGroupNum(byte groupNum, Long toUpdate);
    void deleteGroupById(Long groupId);
}
