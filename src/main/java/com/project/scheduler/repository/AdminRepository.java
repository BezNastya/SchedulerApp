package com.project.scheduler.repository;

import com.project.scheduler.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Transactional
    @Modifying
    @Query(
            value = "SELECT userId FROM Admin WHERE email = :adminEmail")
    Long findIdByEmail(@Param(value = "adminEmail") final String adminEmail);
}
