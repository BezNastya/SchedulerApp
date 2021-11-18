package com.project.scheduler.service.impl;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.StudentRepository;
import com.project.scheduler.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public Student save(Student s) {
        return studentRepository.save(s);
    }

    /*
        @Override
        public Student findByLastName(String username) {
            return null;
        }

        @Override
        public Student findByEmail(String email) {
            return null;
        }
    */
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
}
