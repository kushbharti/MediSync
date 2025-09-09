package com.medisync.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Admin Entity - Represents admin-specific information in the MediSync system
 */
@Entity
@Table(name = "admins")
@EntityListeners(AuditingEntityListener.class)
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
    
    @Column(name = "admin_level")
    private Integer adminLevel = 1; // 1 = Hospital Admin, 2 = System Admin
    
    @Column(name = "permissions", columnDefinition = "TEXT")
    private String permissions;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Constructors
    public Admin() {}
    
    public Admin(User user, Integer adminLevel) {
        this.user = user;
        this.adminLevel = adminLevel;
    }
    
    // Getters and Setters
    public Long getAdminId() {
        return adminId;
    }
    
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Integer getAdminLevel() {
        return adminLevel;
    }
    
    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }
    
    public String getPermissions() {
        return permissions;
    }
    
    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
