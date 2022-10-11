package com.project.scheduler.service;

import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Teacher;
import com.project.scheduler.exceptions.UserNotFoundException;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeacherService {

    private final Logger logger = LoggerFactory.getLogger(TeacherService.class);
    private final TeacherRepository teacherRepository;
    private final GroupCourseRepository groupCourseRepository;


    @Autowired
    public TeacherService(TeacherRepository teacherRepository, GroupCourseRepository groupCourseRepository) {
        this.teacherRepository = teacherRepository;
        this.groupCourseRepository = groupCourseRepository;
    }

    
    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    
    public Optional<Teacher> findById(long id) {
        return teacherRepository.findById(id);
    }

    
    public Teacher save(Teacher teacher) {
        logger.warn("Saving teacher");
        return teacherRepository.save(teacher);
    }

    
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    
    public void delete(final long id) {
        teacherRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
        teacherRepository.deleteById(id);
    }

    
    public void updateAcademicDegree(final long id, final String academicDegree) {
        teacherRepository.updateAcademicDegree(id, academicDegree);
    }

    
    public void updateDepartment(final long id, final String department) {
        teacherRepository.updateDepartment(id, department);
    }

    
    public Teacher deleteGroupForUserByGroupCourse(Long teacherId, GroupCourse groupCourse) {
        Teacher teacher = findById(teacherId).get();
        Set<GroupCourse> groupCourses = teacher.getGroupCourse();
        groupCourses.remove(groupCourse);
        teacher.setGroupCourse(groupCourses);
        return save(teacher);
    }

    
    public Teacher addGroupForUser(Long teacherId, GroupCourse groupCourse) {
        Teacher teacher = findById(teacherId).get();
        Set<GroupCourse> groupCourses = teacher.getGroupCourse();
        groupCourses.add(groupCourse);
        teacher.setGroupCourse(groupCourses);
        return save(teacher);
    }

}
