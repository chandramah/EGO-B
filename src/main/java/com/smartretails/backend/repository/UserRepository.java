package com.smartretails.backend.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smartretails.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.failedLoginAttempts = :attempts WHERE  u.username = :username")
    void updateFailedLoginAttempts(@Param("username") String username, @Param("attempts") Integer attempts);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lastLogin = :loginTime WHERE u.username = :username")
    void updateLastLogin(@Param("username") String username, @Param("loginTime") LocalDateTime loginTime);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lockedUntil = :lockedUntil WHERE u.username = :username")
    void lockUser(@Param("username") String username, @Param("lockedUntil") LocalDateTime lockedUntil);
}



