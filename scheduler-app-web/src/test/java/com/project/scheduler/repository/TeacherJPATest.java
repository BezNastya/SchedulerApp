package com.project.scheduler.repository;

import com.project.scheduler.entity.Role;
import com.project.scheduler.entity.Teacher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
public class TeacherJPATest {

    @Autowired
    TeacherRepository teacherRepository;

    @Test
    void shouldReturnTrueWhenRepoIsNotEmpty() {
        List<Teacher> teachers = teacherRepository.findAll();
        assertNotNull(teachers);
    }

    @Test
    void shouldReturnTrueWhenRepoIsEmpty() {
        List<Teacher> teachers = any();
        assertNull(teachers);
    }

    @Test
    public void teachersTest() {
        Teacher teacher = new Teacher();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        teacher.setEmail("teacher@ukma.edu.ua");
        teacher.setPassword(encoder.encode("teacher"));
        teacher.setAcademicDegree("Master");
        teacher.setFirstName("Alina");
        teacher.setLastName("Petrivna");
        teacher.setDepartment("FI");
        teacher.setRole(Role.TEACHER);
        teacher.setAuthorized(true);
        teacherRepository.save(teacher);
        Iterable<Teacher> teachers = teacherRepository.findAll();
        Assertions.assertThat(teachers).hasSize(1);
    }

    @Test
    void shouldReturnTrueIfNameIsAlina(){
        Teacher teacher = new Teacher();
        teacher.setFirstName("Alina");
        org.junit.jupiter.api.Assertions.assertEquals(teacher.getFirstName(),"Alina");
    }
    @Test
    void shouldReturnTrueIfTeacherRepoLess50(){
    assertTrue(teacherRepository.findAll().size()<50);
    }
}


