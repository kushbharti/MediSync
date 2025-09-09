package com.medisync.controller;

import com.medisync.dto.MessageResponse;
import com.medisync.model.Hospital;
import com.medisync.model.User;
import com.medisync.repository.HospitalRepository;
import com.medisync.repository.UserRepository;
import com.medisync.service.DoctorService;
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
 * Admin Controller - Handles admin-related requests
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    /**
     * Get all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Get user by ID
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update user
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            if (userDetails.getName() != null) {
                user.setName(userDetails.getName());
            }
            if (userDetails.getEmail() != null) {
                user.setEmail(userDetails.getEmail());
            }
            if (userDetails.getIsActive() != null) {
                user.setIsActive(userDetails.getIsActive());
            }
            
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete user
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all hospitals
     */
    @GetMapping("/hospitals")
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        return ResponseEntity.ok(hospitals);
    }

    /**
     * Add new hospital
     */
    @PostMapping("/hospitals")
    public ResponseEntity<Hospital> addHospital(@RequestBody Hospital hospital) {
        Hospital savedHospital = hospitalRepository.save(hospital);
        return ResponseEntity.ok(savedHospital);
    }

    /**
     * Update hospital
     */
    @PutMapping("/hospitals/{id}")
    public ResponseEntity<?> updateHospital(@PathVariable Long id, @RequestBody Hospital hospitalDetails) {
        Optional<Hospital> hospitalOpt = hospitalRepository.findById(id);
        if (hospitalOpt.isPresent()) {
            Hospital hospital = hospitalOpt.get();
            
            if (hospitalDetails.getName() != null) {
                hospital.setName(hospitalDetails.getName());
            }
            if (hospitalDetails.getAddress() != null) {
                hospital.setAddress(hospitalDetails.getAddress());
            }
            if (hospitalDetails.getContactInfo() != null) {
                hospital.setContactInfo(hospitalDetails.getContactInfo());
            }
            if (hospitalDetails.getApiEndpoint() != null) {
                hospital.setApiEndpoint(hospitalDetails.getApiEndpoint());
            }
            if (hospitalDetails.getIsActive() != null) {
                hospital.setIsActive(hospitalDetails.getIsActive());
            }
            
            Hospital updatedHospital = hospitalRepository.save(hospital);
            return ResponseEntity.ok(updatedHospital);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete hospital
     */
    @DeleteMapping("/hospitals/{id}")
    public ResponseEntity<?> deleteHospital(@PathVariable Long id) {
        Optional<Hospital> hospital = hospitalRepository.findById(id);
        if (hospital.isPresent()) {
            hospitalRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Hospital deleted successfully"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get system statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalUsers", userRepository.countTotalUsers());
        stats.put("activeUsers", userRepository.countActiveUsers());
        stats.put("totalPatients", patientService.getTotalPatientCount());
        stats.put("totalDoctors", doctorService.getTotalDoctorCount());
        stats.put("totalHospitals", hospitalRepository.countTotalHospitals());
        stats.put("activeHospitals", hospitalRepository.countActiveHospitals());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * Search users by role
     */
    @GetMapping("/users/role/{roleName}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        List<User> users = userRepository.findByRoleName(roleName.toUpperCase());
        return ResponseEntity.ok(users);
    }

    /**
     * Toggle user active status
     */
    @PatchMapping("/users/{id}/toggle-status")
    public ResponseEntity<?> toggleUserStatus(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setIsActive(!user.getIsActive());
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
