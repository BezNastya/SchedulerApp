package com.project.scheduler.repository;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupCourseRepository extends JpaRepository<GroupCourse, Long> {
    @Transactional
    @Modifying
    @Query(value = "SELECT u FROM GroupCourse u WHERE u.course = :course")
    List<GroupCourse> findAllGroupsCourseByCourse(@Param(value = "course") Course course);
    GroupCourse findGroupCourseByCourseAndGroupNum(Course courseId, byte group);


//    @Query(value = "SELECT u FROM GroupCourse u WHERE u.students = :student" )
//    List<GroupCourse> findAllByStudents(@Param(value = "student") final long id);

//    @Query(value = "SELECT u FROM GroupCourse u JOIN Student t WHERE t.userId = :id")
//    List<GroupCourse> findAllByStudents(@Param(value = "id") final long id);

//    select s.courses from Student s join s.courses where s.id = :id
    @Query(value = "SELECT distinct s.groupCourse FROM Student s JOIN s.groupCourse WHERE s.userId = :id")
    List<GroupCourse> findAllByStudentsId(@Param(value = "id") final long id);


}
