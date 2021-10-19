package com.project.scheduler.service;

import com.project.scheduler.entity.Student;

import java.util.List;


public interface StudentService {
    //Student findByLastName(String username);
    //Student findByEmail(String email);
    Student save(Student s);
    Student findById(long id);
    List<Student>findAll();
    void updateFaculty(final Student student, final String faculty);
    void updateSpeciality(final Student student, final String speciality);
    void updateTicketNumber(final Student student, final String ticketNumber);
    //void updateAdmissionYear(final Student student, final int admissionYear);
    void delete(Student s);
    //void joinCourse(Course course,Student student);
}
