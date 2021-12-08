package com.project.scheduler.repository;

import com.project.scheduler.entity.User;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(@Param(value = "userEmail") final String userEmail);

}
