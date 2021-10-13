package com.project.scheduler.repository;

import com.project.scheduler.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Course,Long> {


}
