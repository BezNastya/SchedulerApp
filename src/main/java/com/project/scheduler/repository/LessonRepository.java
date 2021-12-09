package com.project.scheduler.repository;

import com.project.scheduler.entity.Course;
import com.project.scheduler.entity.GroupCourse;
import com.project.scheduler.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {

//    @Query(value = "SELECT s.lessons FROM GroupCourse s JOIN s.lessons WHERE s.id = :id")
    List<Lesson> findLessonsByGroupCourse(GroupCourse groupCourse);

    void deleteLessonsByGroupCourse_Course(Course course);

    List<Lesson> findLessonsByDate_Week(int week);

    //@Query(value = "SELECT g.lessons FROM EducationUser u JOIN u.groupCourse g WHERE u.userId = :id")
    //List<Collection<Lesson>> findLessonsByEducationUserId(@Param(value = "id") final long id);


}
