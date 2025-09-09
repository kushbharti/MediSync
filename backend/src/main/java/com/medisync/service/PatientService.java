package com.medisync.service;

import com.medisync.model.MedicalRecord;
import com.medisync.model.Patient;
import com.medisync.repository.MedicalRecordRepository;
import com.medisync.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Patient Service - Business logic for patient operations
 */
@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Get patient by ID
     */
    public Optional<Patient> getPatientById(Long patientId) {
        return patientRepository.findById(patientId);
    }

    /**
     * Get patient by user ID
     */
    public Optional<Patient> getPatientByUserId(Long userId) {
        return patientRepository.findByUser_UserId(userId);
    }

    /**
     * Get patient by email
     */
    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByUserEmail(email);
    }

    /**
     * Update patient information
     */
    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * Get patient's complete medical history across all hospitals
     */
    public List<MedicalRecord> getPatientMedicalHistory(Long patientId) {
        return medicalRecordRepository.findCompletePatientHistory(patientId);
    }

    /**
     * Get recent medical records for patient
     */
    public List<MedicalRecord> getRecentMedicalRecords(Long patientId) {
        return medicalRecordRepository.findRecentRecordsByPatient(patientId);
    }

    /**
     * Get patient's medical history within date range
     */
    public List<MedicalRecord> getPatientHistoryByDateRange(Long patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return medicalRecordRepository.findByPatientAndDateRange(patientId, startDate, endDate);
    }

    /**
     * Get all patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Search patients by name
     */
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.searchByName(name);
    }

    /**
     * Get patients by blood group
     */
    public List<Patient> getPatientsByBloodGroup(String bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup);
    }

    /**
     * Count total patients
     */
    public Long getTotalPatientCount() {
        return patientRepository.countTotalPatients();
    }
}
