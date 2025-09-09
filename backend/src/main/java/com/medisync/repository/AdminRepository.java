package com.medisync.repository;

import com.medisync.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Admin Repository - Data access layer for Admin entity
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    /**
     * Find admin by user ID
     */
    Optional<Admin> findByUser_UserId(Long userId);
    
    /**
     * Find admin by user email
     */
    @Query("SELECT a FROM Admin a JOIN a.user u WHERE u.email = :email")
    Optional<Admin> findByUserEmail(@Param("email") String email);
    
    /**
     * Find admins by admin level
     */
    List<Admin> findByAdminLevel(Integer adminLevel);
    
    /**
     * Count admins by level
     */
    @Query("SELECT COUNT(a) FROM Admin a WHERE a.adminLevel = :level")
    Long countAdminsByLevel(@Param("level") Integer level);
    
    /**
     * Search admins by name
     */
    @Query("SELECT a FROM Admin a JOIN a.user u WHERE u.name LIKE %:name%")
    List<Admin> searchByName(@Param("name") String name);
}
