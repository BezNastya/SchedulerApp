package com.project.scheduler.repository;

import com.project.scheduler.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findCourseByName(String name);
}
