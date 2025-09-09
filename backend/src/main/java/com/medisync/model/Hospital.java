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
 * Hospital Entity - Represents hospitals in the MediSync system
 */
@Entity
@Table(name = "hospitals")
@EntityListeners(AuditingEntityListener.class)
public class Hospital {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;
    
    @NotBlank
    @Size(max = 200)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @Size(max = 100)
    @Column(name = "contact_info")
    private String contactInfo;
    
    @Size(max = 255)
    @Column(name = "api_endpoint")
    private String apiEndpoint;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Doctor> doctors = new HashSet<>();
    
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MedicalRecord> medicalRecords = new HashSet<>();
    
    // Constructors
    public Hospital() {}
    
    public Hospital(String name, String address, String contactInfo) {
        this.name = name;
        this.address = address;
        this.contactInfo = contactInfo;
    }
    
    // Getters and Setters
    public Long getHospitalId() {
        return hospitalId;
    }
    
    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getContactInfo() {
        return contactInfo;
    }
    
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    
    public String getApiEndpoint() {
        return apiEndpoint;
    }
    
    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Set<Doctor> getDoctors() {
        return doctors;
    }
    
    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }
    
    public Set<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
    
    public void setMedicalRecords(Set<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}
