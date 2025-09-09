package com.medisync.repository;

import com.medisync.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MedicalRecord Repository - Data access layer for MedicalRecord entity
 */
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    
    /**
     * Find medical records by patient ID
     */
    List<MedicalRecord> findByPatient_PatientIdOrderByVisitDateDesc(Long patientId);
    
    /**
     * Find medical records by doctor ID
     */
    List<MedicalRecord> findByDoctor_DoctorIdOrderByVisitDateDesc(Long doctorId);
    
    /**
     * Find medical records by hospital ID
     */
    List<MedicalRecord> findByHospital_HospitalIdOrderByVisitDateDesc(Long hospitalId);
    
    /**
     * Find medical records by patient and date range
     */
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.patient.patientId = :patientId " +
           "AND mr.visitDate BETWEEN :startDate AND :endDate ORDER BY mr.visitDate DESC")
    List<MedicalRecord> findByPatientAndDateRange(@Param("patientId") Long patientId, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
    
    /**
     * Find medical records by diagnosis containing
     */
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.diagnosis LIKE %:diagnosis% ORDER BY mr.visitDate DESC")
    List<MedicalRecord> findByDiagnosisContaining(@Param("diagnosis") String diagnosis);
    
    /**
     * Find recent medical records by patient
     */
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.patient.patientId = :patientId " +
           "ORDER BY mr.visitDate DESC")
    List<MedicalRecord> findRecentRecordsByPatient(@Param("patientId") Long patientId);
    
    /**
     * Count medical records by patient
     */
    @Query("SELECT COUNT(mr) FROM MedicalRecord mr WHERE mr.patient.patientId = :patientId")
    Long countRecordsByPatient(@Param("patientId") Long patientId);
    
    /**
     * Count medical records by doctor
     */
    @Query("SELECT COUNT(mr) FROM MedicalRecord mr WHERE mr.doctor.doctorId = :doctorId")
    Long countRecordsByDoctor(@Param("doctorId") Long doctorId);
    
    /**
     * Count medical records by hospital
     */
    @Query("SELECT COUNT(mr) FROM MedicalRecord mr WHERE mr.hospital.hospitalId = :hospitalId")
    Long countRecordsByHospital(@Param("hospitalId") Long hospitalId);
    
    /**
     * Find patient's complete medical history across all hospitals
     */
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.patient.patientId = :patientId " +
           "ORDER BY mr.visitDate DESC")
    List<MedicalRecord> findCompletePatientHistory(@Param("patientId") Long patientId);
}
