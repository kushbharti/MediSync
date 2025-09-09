package com.medisync.controller;

import com.medisync.model.Hospital;
import com.medisync.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Public Controller - Handles public endpoints that don't require authentication
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private HospitalRepository hospitalRepository;

    /**
     * Get system information
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "MediSync");
        info.put("version", "1.0.0");
        info.put("description", "Cross-Hospital Interoperability System");
        info.put("author", "MediSync Team");
        info.put("status", "operational");
        
        return ResponseEntity.ok(info);
    }

    /**
     * Get public hospital directory
     */
    @GetMapping("/hospitals")
    public ResponseEntity<List<Hospital>> getPublicHospitals() {
        List<Hospital> hospitals = hospitalRepository.findByIsActive(true);
        return ResponseEntity.ok(hospitals);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", System.currentTimeMillis());
        health.put("service", "MediSync Backend");
        
        return ResponseEntity.ok(health);
    }

    /**
     * Get API documentation info
     */
    @GetMapping("/api-docs")
    public ResponseEntity<Map<String, Object>> getApiDocs() {
        Map<String, Object> docs = new HashMap<>();
        docs.put("title", "MediSync API Documentation");
        docs.put("version", "v1.0");
        docs.put("baseUrl", "/api");
        docs.put("authentication", "Bearer Token (JWT)");
        docs.put("endpoints", Map.of(
            "auth", "/api/auth/*",
            "patients", "/api/patients/*",
            "doctors", "/api/doctors/*",
            "admin", "/api/admin/*",
            "sync", "/api/sync/*",
            "public", "/api/public/*"
        ));
        
        return ResponseEntity.ok(docs);
    }
}
