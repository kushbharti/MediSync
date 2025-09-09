package com.medisync.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Role Entity - Defines user roles in the MediSync system
 * Supports PATIENT, DOCTOR, and ADMIN roles
 */
@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "role_name", unique = true)
    private String roleName;
    
    @Size(max = 255)
    private String description;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
    
    // Constructors
    public Role() {}
    
    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Set<User> getUsers() {
        return users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

/**
 * Enum for predefined role names
 */
enum RoleName {
    PATIENT,
    DOCTOR, 
    ADMIN
}
