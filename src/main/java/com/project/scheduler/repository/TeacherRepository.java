package com.project.scheduler.repository;

import com.project.scheduler.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findById(long id);
    Teacher findByEmail(String email);
    Teacher findByLastName(String lastName);
}
