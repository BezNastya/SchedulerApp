package com.project.scheduler.repository;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Lesson;
import com.project.scheduler.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupCourseRepository extends JpaRepository<GroupCourse, Long> {
    @Transactional
    @Modifying
    @Query(value = "SELECT u FROM GroupCourse u WHERE u.course = :course")
    List<GroupCourse> findAllGroupsCourseByCourse(@Param(value = "course") Course course);
    GroupCourse findGroupCourseByCourseAndGroupNum(Course courseId, byte group);

    @Query(value = "SELECT distinct s.groupCourse FROM EducationUser s JOIN s.groupCourse WHERE s.userId = :id")
    List<GroupCourse> findGroupCoursesByEducationUserId(@Param(value = "id") final long id);

}
