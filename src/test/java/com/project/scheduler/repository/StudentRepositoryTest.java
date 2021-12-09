package com.project.scheduler.repository;


import com.project.scheduler.entity.Student;
import com.project.scheduler.repository.StudentRepository;
import com.project.scheduler.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student("Serdiuk", "Andrii", "FI", "SE", "andrii.serdiuk@ukma.edu.ua", "password"),
            new Student("Anastasiia", "Bezruka", "FI", "SE", "anastasiia.bezruka@ukma.edu.ua", "password"),
            new Student("Yevhenii", "Ostashko", "FI", "SE", "yevhenii.ostashko@ukma.edu.ua", "password"),
            new Student("Denys", "Tsabut", "FI", "CS", "denys.tsabut@ukma.edu.ua", "password"),
            new Student("Artur", "Onopriichuk", "FI", "SE", "artur.onopriichuk@ukma.edu.ua", "password")
            );

    @BeforeAll
    void saveStudents() {
        studentRepository.saveAll(STUDENTS);
    }

    @Test
    void shouldReturnAll_whenFindAll() {
        Assertions.assertEquals(STUDENTS, studentRepository.findAll());
    }

    @Test
    void shouldReturnNobody_whenDeleteAll() {
        studentRepository.deleteAll();
        Assertions.assertTrue(studentRepository.findAll().isEmpty());
    }

    @Test
    void shouldThrowException_whenAddInvalidStudent() {
        Student student = new Student(null, "Andrii", "IF", "ES", "andrii.serdiuk_ukma.edu.ua", "password");
        Assertions.assertThrows(ConstraintViolationException.class, () -> studentRepository.save(student));
    }

    @Test
    void shouldReturnNull_whenFindByInvalidEmail() {
        Assertions.assertNull(studentRepository.findByEmail("hello,world!@ukma.edu.ua").orElse(null));
    }

    @Test
    void shouldReturnCorrectStudent_whenFindByCorrectEmail() {
        Assertions.assertEquals(studentRepository.findByEmail("andrii.serdiuk@ukma.edu.ua"),
                STUDENTS.stream().filter(student -> "andrii.serdiuk@ukma.edu.ua".equals(student.getEmail())).findFirst());
    }

    @Test
    void shouldFail_whenNonExistingEmailPassed() {
        Assertions.assertEquals(studentRepository.findByEmail("wrongEmail@ukma.edu.ua"), Optional.empty());
    }


    @Test
    void shouldReturnUpdatedFaculty_whenHasBeenUpdated() {
        Optional<Student> optional = STUDENTS.stream().filter(student -> "yevhenii.ostashko@ukma.edu.ua".equals(student.getEmail())).findFirst();
        if (optional.isPresent()) {
            long id = optional.get().getUserId();
            final String updatedFaculty = "FIN";
            studentRepository.updateFaculty(id, updatedFaculty);
            Assertions.assertEquals(studentRepository.getById(id).getFaculty(), updatedFaculty);
        }
    }

    @Test
    void shouldReturnUpdatedSpeciality_whenHasBeenUpdated() {
        Optional<Student> optional = STUDENTS.stream().filter(student -> "denys.tsabut@ukma.edu.ua".equals(student.getEmail())).findFirst();
        if (optional.isPresent()) {
            long id = optional.get().getUserId();
            final String updatedSpeciality = "CS";
            studentRepository.updateFaculty(id, updatedSpeciality);
            Assertions.assertEquals(studentRepository.getById(id).getFaculty(), updatedSpeciality);
        }
    }

    @Test
    void shouldReturnUpdatedTicketNumber_whenHasBeenUpdated() {
        Optional<Student> optional = STUDENTS.stream().filter(student -> "artur.onopriichuk@ukma.edu.ua".equals(student.getEmail())).findFirst();
        if (optional.isPresent()) {
            long id = optional.get().getUserId();
            final String updatedTicketNumber = "123456789";
            studentRepository.updateFaculty(id, updatedTicketNumber);
            Assertions.assertEquals(studentRepository.getById(id).getFaculty(), updatedTicketNumber);
        }
    }

}
