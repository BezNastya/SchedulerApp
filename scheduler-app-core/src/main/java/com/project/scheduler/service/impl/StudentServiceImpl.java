package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.StudentRepository;
import com.project.scheduler.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GroupCourseRepository groupCourseRepository;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              GroupCourseRepository groupCourseRepository) {
        this.studentRepository = studentRepository;
        this.groupCourseRepository = groupCourseRepository;
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public Student save(Student s) {
        return studentRepository.save(s);
    }

    @Override
    public Student findById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void delete(Student s) {
        studentRepository.delete(s);
    }
    /*
    @Override
    public void joinCourse(Course course,Student student) {
//        student.setCourse(course);
    }

     */

    @Override
    public void updateFaculty(final Student student, final String faculty) {
        studentRepository.updateFaculty(student.getUserId(), faculty);
    }

    @Override
    public void updateSpeciality(final Student student, final String speciality) {
        studentRepository.updateSpeciality(student.getUserId(), speciality);
    }

    @Override
    public void updateTicketNumber(final Student student, final String ticketNumber) {
        studentRepository.updateTicketNumber(student.getUserId(), ticketNumber);
    }

    @Override
    public Student addGroupForUser(Long studentId, Course course, byte groupNum) {
        GroupCourse go = groupCourseRepository.findGroupCourseByCourseAndGroupNum(course, groupNum);
        Student student = findById(studentId);
        Set<GroupCourse> groupCourses = student.getGroupCourse();
        groupCourses.add(go);
        student.setGroupCourse(groupCourses);
        return save(student);
    }

    @Override
    public Student deleteGroupForUser(Long studentId, Course course, byte groupNum) {
        GroupCourse go = groupCourseRepository.findGroupCourseByCourseAndGroupNum(course, groupNum);
        Student student = findById(studentId);
        Set<GroupCourse> groupCourses = student.getGroupCourse();
        groupCourses.remove(go);
        student.setGroupCourse(groupCourses);
        return save(student);
    }

    @CacheEvict(cacheNames = "groups", key = "#studentId")
    @Override
    public Student deleteGroupForUserByGroupCourse(Long studentId, GroupCourse groupCourse) {
        Student student = findById(studentId);
        Set<GroupCourse> groupCourses = student.getGroupCourse();
        groupCourses.remove(groupCourse);
        student.setGroupCourse(groupCourses);
        return save(student);
    }
}
