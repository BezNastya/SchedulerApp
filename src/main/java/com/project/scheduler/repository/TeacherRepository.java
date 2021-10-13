package com.project.scheduler.repository;

import com.project.scheduler.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Transactional
    @Modifying
    @Query("update Teacher u set u.academicDegree = :degree where u.userId = :id")
    void updateAcademicDegree(@Param(value = "id") final long id, @Param(value = "degree") final String degree);


    @Transactional
    @Modifying
    @Query("update Teacher u set u.department = :department where u.userId = :id")
    void updateDepartment(@Param(value = "id") final long id, @Param(value = "department") final String department);


}
