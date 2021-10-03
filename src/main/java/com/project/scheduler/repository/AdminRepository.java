package com.project.scheduler.repository;

import com.project.scheduler.entity.Admin;
import com.project.scheduler.entity.ScheduleDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
    Admin findById(long id);
    void deleteAdminById(long id);

}
