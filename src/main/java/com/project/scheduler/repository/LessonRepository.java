package com.project.scheduler.repository;

import com.project.scheduler.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {

}
