package com.medisync.service;

import com.medisync.model.Doctor;
import com.medisync.model.MedicalRecord;
import com.medisync.model.Patient;
import com.medisync.repository.DoctorRepository;
import com.medisync.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Doctor Service - Business logic for doctor operations
 */
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Get doctor by ID
     */
    public Optional<Doctor> getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId);
    }

    /**
     * Get doctor by user ID
     */
    public Optional<Doctor> getDoctorByUserId(Long userId) {
        return doctorRepository.findByUser_UserId(userId);
    }

    /**
     * Get doctor by email
     */
    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepository.findByUserEmail(email);
    }

    /**
     * Update doctor information
     */
    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    /**
     * Get all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Get doctors by specialization
     */
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }

    /**
     * Get doctors by hospital
     */
    public List<Doctor> getDoctorsByHospital(Long hospitalId) {
        return doctorRepository.findByHospital_HospitalId(hospitalId);
    }

    /**
     * Search doctors by name
     */
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepository.searchByName(name);
    }

    /**
     * Get medical records created by doctor
     */
    public List<MedicalRecord> getDoctorMedicalRecords(Long doctorId) {
        return medicalRecordRepository.findByDoctor_DoctorIdOrderByVisitDateDesc(doctorId);
    }

    /**
     * Count total doctors
     */
    public Long getTotalDoctorCount() {
        return doctorRepository.countTotalDoctors();
    }

    /**
     * Add medical record for patient
     */
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    /**
     * Update medical record
     */
    public Optional<MedicalRecord> updateMedicalRecord(Long recordId, MedicalRecord updatedRecord) {
        Optional<MedicalRecord> existingRecord = medicalRecordRepository.findById(recordId);
        if (existingRecord.isPresent()) {
            MedicalRecord record = existingRecord.get();
            record.setDiagnosis(updatedRecord.getDiagnosis());
            record.setTreatment(updatedRecord.getTreatment());
            record.setPrescription(updatedRecord.getPrescription());
            record.setTestResults(updatedRecord.getTestResults());
            return Optional.of(medicalRecordRepository.save(record));
        }
        return Optional.empty();
    }
}
