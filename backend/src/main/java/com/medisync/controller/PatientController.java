package com.medisync.controller;

import com.medisync.dto.MessageResponse;
import com.medisync.model.MedicalRecord;
import com.medisync.model.Patient;
import com.medisync.security.UserPrincipal;
import com.medisync.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Patient Controller - Handles patient-related requests
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * Get patient profile (accessible by patient themselves, doctors, and admins)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or (hasRole('PATIENT') and @patientService.getPatientByUserId(authentication.principal.id).map(p -> p.patientId).orElse(-1L) == #id)")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update patient information
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PATIENT') and @patientService.getPatientByUserId(authentication.principal.id).map(p -> p.patientId).orElse(-1L) == #id)")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Optional<Patient> patientOpt = patientService.getPatientById(id);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            
            // Update allowed fields
            if (patientDetails.getAge() != null) {
                patient.setAge(patientDetails.getAge());
            }
            if (patientDetails.getGender() != null) {
                patient.setGender(patientDetails.getGender());
            }
            if (patientDetails.getBloodGroup() != null) {
                patient.setBloodGroup(patientDetails.getBloodGroup());
            }
            if (patientDetails.getEmergencyContact() != null) {
                patient.setEmergencyContact(patientDetails.getEmergencyContact());
            }
            if (patientDetails.getMedicalHistory() != null) {
                patient.setMedicalHistory(patientDetails.getMedicalHistory());
            }
            
            Patient updatedPatient = patientService.updatePatient(patient);
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get patient's complete medical history
     */
    @GetMapping("/{id}/history")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or (hasRole('PATIENT') and @patientService.getPatientByUserId(authentication.principal.id).map(p -> p.patientId).orElse(-1L) == #id)")
    public ResponseEntity<List<MedicalRecord>> getPatientHistory(@PathVariable Long id) {
        List<MedicalRecord> history = patientService.getPatientMedicalHistory(id);
        return ResponseEntity.ok(history);
    }

    /**
     * Get patient's recent medical records
     */
    @GetMapping("/{id}/records/recent")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or (hasRole('PATIENT') and @patientService.getPatientByUserId(authentication.principal.id).map(p -> p.patientId).orElse(-1L) == #id)")
    public ResponseEntity<List<MedicalRecord>> getRecentRecords(@PathVariable Long id) {
        List<MedicalRecord> records = patientService.getRecentMedicalRecords(id);
        return ResponseEntity.ok(records);
    }

    /**
     * Get current patient's profile (for authenticated patient)
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> getCurrentPatientProfile(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Optional<Patient> patient = patientService.getPatientByUserId(userPrincipal.getId());
        
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Patient profile not found"));
        }
    }

    /**
     * Get current patient's medical history
     */
    @GetMapping("/profile/history")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> getCurrentPatientHistory(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Optional<Patient> patient = patientService.getPatientByUserId(userPrincipal.getId());
        
        if (patient.isPresent()) {
            List<MedicalRecord> history = patientService.getPatientMedicalHistory(patient.get().getPatientId());
            return ResponseEntity.ok(history);
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Patient profile not found"));
        }
    }

    /**
     * Search patients by name (Admin and Doctor only)
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<List<Patient>> searchPatients(@RequestParam String name) {
        List<Patient> patients = patientService.searchPatientsByName(name);
        return ResponseEntity.ok(patients);
    }

    /**
     * Get all patients (Admin only)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
}
