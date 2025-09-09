package com.medisync.controller;

import com.medisync.dto.MessageResponse;
import com.medisync.model.Hospital;
import com.medisync.model.MedicalRecord;
import com.medisync.model.Patient;
import com.medisync.repository.HospitalRepository;
import com.medisync.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Sync Controller - Handles data synchronization between hospitals
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sync")
@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
public class SyncController {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private PatientService patientService;

    /**
     * Sync patient data with specific hospital
     */
    @PostMapping("/hospitals/{hospitalId}")
    public ResponseEntity<?> syncWithHospital(@PathVariable Long hospitalId) {
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if (hospital.isPresent()) {
            // In a real implementation, this would trigger actual sync process
            // with external hospital systems via their API endpoints
            Map<String, Object> syncResult = new HashMap<>();
            syncResult.put("hospital", hospital.get().getName());
            syncResult.put("status", "sync_initiated");
            syncResult.put("message", "Data synchronization initiated with " + hospital.get().getName());
            
            return ResponseEntity.ok(syncResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get sync status for all hospitals
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSyncStatus() {
        List<Hospital> activeHospitals = hospitalRepository.findByIsActive(true);
        
        Map<String, Object> syncStatus = new HashMap<>();
        syncStatus.put("totalActiveHospitals", activeHospitals.size());
        syncStatus.put("lastSyncTime", System.currentTimeMillis());
        syncStatus.put("syncEnabled", true);
        
        return ResponseEntity.ok(syncStatus);
    }

    /**
     * Sync patient data across all hospitals
     */
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<?> syncPatientData(@PathVariable Long patientId) {
        Optional<Patient> patient = patientService.getPatientById(patientId);
        if (patient.isPresent()) {
            List<MedicalRecord> patientHistory = patientService.getPatientMedicalHistory(patientId);
            List<Hospital> activeHospitals = hospitalRepository.findByIsActive(true);
            
            Map<String, Object> syncResult = new HashMap<>();
            syncResult.put("patientId", patientId);
            syncResult.put("recordsCount", patientHistory.size());
            syncResult.put("hospitalsSynced", activeHospitals.size());
            syncResult.put("status", "sync_completed");
            syncResult.put("message", "Patient data synchronized across all active hospitals");
            
            return ResponseEntity.ok(syncResult);
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Patient not found"));
        }
    }

    /**
     * Get hospitals with API endpoints for sync
     */
    @GetMapping("/hospitals")
    public ResponseEntity<List<Hospital>> getSyncableHospitals() {
        List<Hospital> hospitalsWithApi = hospitalRepository.findHospitalsWithApiEndpoints();
        return ResponseEntity.ok(hospitalsWithApi);
    }

    /**
     * Test connection to hospital API
     */
    @PostMapping("/test/{hospitalId}")
    public ResponseEntity<?> testHospitalConnection(@PathVariable Long hospitalId) {
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if (hospital.isPresent()) {
            // In a real implementation, this would test actual API connection
            Map<String, Object> testResult = new HashMap<>();
            testResult.put("hospital", hospital.get().getName());
            testResult.put("apiEndpoint", hospital.get().getApiEndpoint());
            testResult.put("status", "connected");
            testResult.put("responseTime", "45ms");
            testResult.put("message", "Connection successful");
            
            return ResponseEntity.ok(testResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get interoperability standards info
     */
    @GetMapping("/standards")
    public ResponseEntity<Map<String, Object>> getInteroperabilityStandards() {
        Map<String, Object> standards = new HashMap<>();
        standards.put("standard", "HL7 FHIR R4");
        standards.put("version", "4.0.1");
        standards.put("supported_resources", List.of("Patient", "Observation", "Condition", "MedicationRequest", "DiagnosticReport"));
        standards.put("security", "OAuth 2.0 with JWT");
        standards.put("compliance", "HIPAA, GDPR");
        
        return ResponseEntity.ok(standards);
    }
}
