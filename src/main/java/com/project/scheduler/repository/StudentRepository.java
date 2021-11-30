package com.project.scheduler.repository;

import com.project.scheduler.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(@Param(value = "studentEmail") final String studentEmail);

    @Transactional
    @Modifying
    @Query("update Student u set u.faculty = :faculty where u.userId = :id")
    void updateFaculty(@Param(value = "id") final long id, @Param(value = "faculty") final String faculty);


    @Transactional
    @Modifying
    @Query("update Student u set u.specialty = :speciality where u.userId = :id")
    void updateSpeciality(@Param(value = "id") final long id, @Param(value = "speciality") final String speciality);

    @Transactional
    @Modifying
    @Query("update Student u set u.studTicketSeries = :ticket where u.userId = :id")
    void updateTicketNumber(@Param(value = "id") final long id, @Param(value = "ticket") final String ticketNumber);


}
