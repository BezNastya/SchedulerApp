package com.project.scheduler.service;

import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.Student;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    //Student findByLastName(String username);
    Optional<Student> findByEmail(String email);
    Student save(Student s);
    Student findById(long id);
    List<Student>findAll();
    void updateFaculty(final Student student, final String faculty);
    void updateSpeciality(final Student student, final String speciality);
    void updateTicketNumber(final Student student, final String ticketNumber);
    //void updateAdmissionYear(final Student student, final int admissionYear);
    void delete(Student s);
}
