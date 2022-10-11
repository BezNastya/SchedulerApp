package com.project.scheduler.repository;

import com.project.scheduler.entity.GroupCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupCourseRepository extends JpaRepository<GroupCourse, Long> {

    @Query(value = "SELECT distinct s.groupCourse FROM EducationUser s JOIN s.groupCourse WHERE s.userId = :id")
    List<GroupCourse> findGroupCoursesByEducationUserId(@Param(value = "id") final long id);
}
