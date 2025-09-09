package com.medisync.repository;

import com.medisync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User Repository - Data access layer for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if user exists by email
     */
    Boolean existsByEmail(String email);
    
    /**
     * Find users by role name
     */
    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = :roleName")
    List<User> findByRoleName(@Param("roleName") String roleName);
    
    /**
     * Find active users
     */
    List<User> findByIsActive(Boolean isActive);
    
    /**
     * Find users by name containing (case insensitive)
     */
    List<User> findByNameContainingIgnoreCase(String name);
    
    /**
     * Count total users
     */
    @Query("SELECT COUNT(u) FROM User u")
    Long countTotalUsers();
    
    /**
     * Count active users
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    Long countActiveUsers();
}
