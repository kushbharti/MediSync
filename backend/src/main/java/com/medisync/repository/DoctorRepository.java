package com.medisync.repository;

import com.medisync.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Doctor Repository - Data access layer for Doctor entity
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    /**
     * Find doctor by user ID
     */
    Optional<Doctor> findByUser_UserId(Long userId);
    
    /**
     * Find doctor by user email
     */
    @Query("SELECT d FROM Doctor d JOIN d.user u WHERE u.email = :email")
    Optional<Doctor> findByUserEmail(@Param("email") String email);
    
    /**
     * Find doctor by license number
     */
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    
    /**
     * Find doctors by specialization
     */
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    
    /**
     * Find doctors by hospital ID
     */
    List<Doctor> findByHospital_HospitalId(Long hospitalId);
    
    /**
     * Find doctors by hospital name
     */
    @Query("SELECT d FROM Doctor d JOIN d.hospital h WHERE h.name LIKE %:hospitalName%")
    List<Doctor> findByHospitalName(@Param("hospitalName") String hospitalName);
    
    /**
     * Count doctors by hospital
     */
    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.hospital.hospitalId = :hospitalId")
    Long countDoctorsByHospital(@Param("hospitalId") Long hospitalId);
    
    /**
     * Count total doctors
     */
    @Query("SELECT COUNT(d) FROM Doctor d")
    Long countTotalDoctors();
    
    /**
     * Search doctors by name
     */
    @Query("SELECT d FROM Doctor d JOIN d.user u WHERE u.name LIKE %:name%")
    List<Doctor> searchByName(@Param("name") String name);
}
