package com.medisync.controller;

import com.medisync.dto.JwtResponse;
import com.medisync.dto.LoginRequest;
import com.medisync.dto.MessageResponse;
import com.medisync.dto.SignupRequest;
import com.medisync.model.*;
import com.medisync.repository.*;
import com.medisync.security.JwtUtils;
import com.medisync.security.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller - Handles login and signup requests
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getAuthorities().iterator().next().getAuthority()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        String strRole = signUpRequest.getRole();
        Role userRole = roleRepository.findByRoleName(strRole)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.setRole(userRole);
        User savedUser = userRepository.save(user);

        // Create role-specific profile
        switch (strRole) {
            case "PATIENT":
                Patient patient = new Patient();
                patient.setUser(savedUser);
                patient.setAge(signUpRequest.getAge());
                patient.setGender(signUpRequest.getGender());
                patient.setBloodGroup(signUpRequest.getBloodGroup());
                patient.setEmergencyContact(signUpRequest.getEmergencyContact());
                patientRepository.save(patient);
                break;

            case "DOCTOR":
                Doctor doctor = new Doctor();
                doctor.setUser(savedUser);
                doctor.setSpecialization(signUpRequest.getSpecialization());
                doctor.setLicenseNumber(signUpRequest.getLicenseNumber());
                if (signUpRequest.getHospitalId() != null) {
                    Hospital hospital = hospitalRepository.findById(signUpRequest.getHospitalId())
                            .orElseThrow(() -> new RuntimeException("Error: Hospital not found."));
                    doctor.setHospital(hospital);
                }
                doctorRepository.save(doctor);
                break;

            case "ADMIN":
                Admin admin = new Admin();
                admin.setUser(savedUser);
                admin.setAdminLevel(1); // Default admin level
                adminRepository.save(admin);
                break;

            default:
                throw new RuntimeException("Error: Invalid role specified.");
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
    }
}
