package com.project.scheduler.repository;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
