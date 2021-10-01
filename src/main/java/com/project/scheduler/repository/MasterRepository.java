package com.project.scheduler.repository;

import com.project.scheduler.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
    Admin findById(long id);
}
