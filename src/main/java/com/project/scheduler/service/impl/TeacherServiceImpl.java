package com.project.scheduler.service.impl;

import com.project.scheduler.StartupData;
import com.project.scheduler.entity.*;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.TeacherRepository;
import com.project.scheduler.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final Logger logger = LoggerFactory.getLogger(StartupData.class);
    private final TeacherRepository teacherRepository;
    private final GroupCourseRepository groupCourseRepository;


    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, GroupCourseRepository groupCourseRepository) {
        this.teacherRepository = teacherRepository;
        this.groupCourseRepository = groupCourseRepository;
    }

    @Override
    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public Optional<Teacher> findById(long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        logger.warn("Saving teacher");
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public void delete(final long id) {
        teacherRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
        teacherRepository.deleteById(id);
    }

    @Override
    public void updateAcademicDegree(final long id, final String academicDegree) {
        teacherRepository.updateAcademicDegree(id, academicDegree);
    }

    @Override
    public void updateDepartment(final long id, final String department) {
        teacherRepository.updateDepartment(id, department);
    }

    @Override
    public Teacher deleteGroupForUserByGroupCourse(Long teacherId, GroupCourse groupCourse) {
        Teacher teacher = findById(teacherId).get();
        Set<GroupCourse> groupCourses = teacher.getGroupCourse();
        groupCourses.remove(groupCourse);
        teacher.setGroupCourse(groupCourses);
        return save(teacher);
    }

    @Override
    public Teacher addGroupForUser(Long teacherId, Course course, byte groupNum) {
        GroupCourse go = groupCourseRepository.findGroupCourseByCourseAndGroupNum(course, groupNum);
        Teacher teacher = findById(teacherId).get();
        Set<GroupCourse> groupCourses = teacher.getGroupCourse();
        groupCourses.add(go);
        teacher.setGroupCourse(groupCourses);
        return save(teacher);
    }

}
