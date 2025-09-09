package com.medisync.controller;

import com.medisync.dto.MessageResponse;
import com.medisync.model.Doctor;
import com.medisync.model.MedicalRecord;
import com.medisync.model.Patient;
import com.medisync.security.UserPrincipal;
import com.medisync.service.DoctorService;
import com.medisync.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Doctor Controller - Handles doctor-related requests
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    /**
     * Get doctor profile
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getDoctor(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        if (doctor.isPresent()) {
            return ResponseEntity.ok(doctor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update doctor information
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('DOCTOR') and @doctorService.getDoctorByUserId(authentication.principal.id).map(d -> d.doctorId).orElse(-1L) == #id)")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        Optional<Doctor> doctorOpt = doctorService.getDoctorById(id);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            
            // Update allowed fields
            if (doctorDetails.getSpecialization() != null) {
                doctor.setSpecialization(doctorDetails.getSpecialization());
            }
            if (doctorDetails.getLicenseNumber() != null) {
                doctor.setLicenseNumber(doctorDetails.getLicenseNumber());
            }
            if (doctorDetails.getHospital() != null) {
                doctor.setHospital(doctorDetails.getHospital());
            }
            
            Doctor updatedDoctor = doctorService.updateDoctor(doctor);
            return ResponseEntity.ok(updatedDoctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get current doctor's profile
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getCurrentDoctorProfile(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Optional<Doctor> doctor = doctorService.getDoctorByUserId(userPrincipal.getId());
        
        if (doctor.isPresent()) {
            return ResponseEntity.ok(doctor.get());
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Doctor profile not found"));
        }
    }

    /**
     * Add medical record for patient
     */
    @PostMapping("/{id}/patients/{patientId}/records")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('DOCTOR') and @doctorService.getDoctorByUserId(authentication.principal.id).map(d -> d.doctorId).orElse(-1L) == #id)")
    public ResponseEntity<?> addPatientRecord(@PathVariable Long id, @PathVariable Long patientId, 
                                            @RequestBody MedicalRecord medicalRecord, Authentication authentication) {
        Optional<Doctor> doctorOpt = doctorService.getDoctorById(id);
        Optional<Patient> patientOpt = patientService.getPatientById(patientId);
        
        if (doctorOpt.isPresent() && patientOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            Patient patient = patientOpt.get();
            
            medicalRecord.setDoctor(doctor);
            medicalRecord.setPatient(patient);
            medicalRecord.setHospital(doctor.getHospital());
            
            MedicalRecord savedRecord = doctorService.addMedicalRecord(medicalRecord);
            return ResponseEntity.ok(savedRecord);
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Doctor or Patient not found"));
        }
    }

    /**
     * Update medical record
     */
    @PutMapping("/{id}/records/{recordId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('DOCTOR') and @doctorService.getDoctorByUserId(authentication.principal.id).map(d -> d.doctorId).orElse(-1L) == #id)")
    public ResponseEntity<?> updateMedicalRecord(@PathVariable Long id, @PathVariable Long recordId, 
                                               @RequestBody MedicalRecord medicalRecord) {
        Optional<MedicalRecord> updatedRecord = doctorService.updateMedicalRecord(recordId, medicalRecord);
        if (updatedRecord.isPresent()) {
            return ResponseEntity.ok(updatedRecord.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get doctor's patients/records
     */
    @GetMapping("/{id}/patients")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('DOCTOR') and @doctorService.getDoctorByUserId(authentication.principal.id).map(d -> d.doctorId).orElse(-1L) == #id)")
    public ResponseEntity<List<MedicalRecord>> getDoctorPatients(@PathVariable Long id) {
        List<MedicalRecord> records = doctorService.getDoctorMedicalRecords(id);
        return ResponseEntity.ok(records);
    }

    /**
     * Get current doctor's patients
     */
    @GetMapping("/patients")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getCurrentDoctorPatients(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Optional<Doctor> doctor = doctorService.getDoctorByUserId(userPrincipal.getId());
        
        if (doctor.isPresent()) {
            List<MedicalRecord> records = doctorService.getDoctorMedicalRecords(doctor.get().getDoctorId());
            return ResponseEntity.ok(records);
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Doctor profile not found"));
        }
    }

    /**
     * Search doctors by name or specialization
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<List<Doctor>> searchDoctors(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String specialization) {
        List<Doctor> doctors;
        if (name != null && !name.isEmpty()) {
            doctors = doctorService.searchDoctorsByName(name);
        } else if (specialization != null && !specialization.isEmpty()) {
            doctors = doctorService.getDoctorsBySpecialization(specialization);
        } else {
            doctors = doctorService.getAllDoctors();
        }
        return ResponseEntity.ok(doctors);
    }

    /**
     * Get all doctors (Admin only)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
}
