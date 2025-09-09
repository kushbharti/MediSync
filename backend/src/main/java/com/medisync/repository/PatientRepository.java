package com.medisync.repository;

import com.medisync.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Patient Repository - Data access layer for Patient entity
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    /**
     * Find patient by user ID
     */
    Optional<Patient> findByUser_UserId(Long userId);
    
    /**
     * Find patient by user email
     */
    @Query("SELECT p FROM Patient p JOIN p.user u WHERE u.email = :email")
    Optional<Patient> findByUserEmail(@Param("email") String email);
    
    /**
     * Find patients by blood group
     */
    List<Patient> findByBloodGroup(String bloodGroup);
    
    /**
     * Find patients by gender
     */
    List<Patient> findByGender(String gender);
    
    /**
     * Find patients by age range
     */
    @Query("SELECT p FROM Patient p WHERE p.age BETWEEN :minAge AND :maxAge")
    List<Patient> findByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    /**
     * Count total patients
     */
    @Query("SELECT COUNT(p) FROM Patient p")
    Long countTotalPatients();
    
    /**
     * Search patients by name
     */
    @Query("SELECT p FROM Patient p JOIN p.user u WHERE u.name LIKE %:name%")
    List<Patient> searchByName(@Param("name") String name);
}
