package com.medisync.repository;

import com.medisync.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Role Repository - Data access layer for Role entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Find role by role name
     */
    Optional<Role> findByRoleName(String roleName);
    
    /**
     * Check if role exists by role name
     */
    Boolean existsByRoleName(String roleName);
}
