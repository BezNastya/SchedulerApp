package com.project.scheduler.repository;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findCourseByName(String name);
}
