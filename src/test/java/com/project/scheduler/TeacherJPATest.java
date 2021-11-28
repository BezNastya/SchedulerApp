package com.project.scheduler;

import com.project.scheduler.entity.Teacher;
import com.project.scheduler.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

public class TeacherJPATest {
    @Autowired
    TeacherRepository repository;

    @Test
    public void teachersTest() {
        Iterable<Teacher> teachers = repository.findAll();
        assertThat(teachers).hasSize(2);
    }
}