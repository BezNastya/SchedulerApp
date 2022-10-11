package com.project.scheduler.service;

import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.GroupCourseRepository;
import com.project.scheduler.repository.StudentRepository;
import com.project.scheduler.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupCourseRepository groupCourseRepository;


    @Autowired
    public StudentService(StudentRepository studentRepository,
                          GroupCourseRepository groupCourseRepository) {
        this.studentRepository = studentRepository;
        this.groupCourseRepository = groupCourseRepository;
    }

    
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    
    public Student save(Student s) {
        return studentRepository.save(s);
    }

    
    public Student findById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    
    public void delete(Student s) {
        studentRepository.delete(s);
    }
    /*
    
    public void joinCourse(Course course,Student student) {
//        student.setCourse(course);
    }

     */

    
    public void updateFaculty(final Student student, final String faculty) {
        studentRepository.updateFaculty(student.getUserId(), faculty);
    }

    
    public void updateSpeciality(final Student student, final String speciality) {
        studentRepository.updateSpeciality(student.getUserId(), speciality);
    }

    
    public void updateTicketNumber(final Student student, final String ticketNumber) {
        studentRepository.updateTicketNumber(student.getUserId(), ticketNumber);
    }

    
    public Student addGroupForUser(Long studentId, GroupCourse groupCourse) {
        Student student = findById(studentId);
        Set<GroupCourse> groupCourses = student.getGroupCourse();
        groupCourses.add(groupCourse);
        student.setGroupCourse(groupCourses);
        return save(student);
    }

    public Student deleteGroupForUserByGroupCourse(Long studentId, GroupCourse groupCourse) {
        Student student = findById(studentId);
        Set<GroupCourse> groupCourses = student.getGroupCourse();
        groupCourses.remove(groupCourse);
        student.setGroupCourse(groupCourses);
        return save(student);
    }
}
