package com.medisync.config;

import com.medisync.model.Hospital;
import com.medisync.model.Role;
import com.medisync.repository.HospitalRepository;
import com.medisync.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Data Loader - Initializes database with default roles and sample hospitals
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadRoles();
        loadSampleHospitals();
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            Role patientRole = new Role("PATIENT", "Patient role for accessing personal medical records");
            Role doctorRole = new Role("DOCTOR", "Doctor role for managing patient records");
            Role adminRole = new Role("ADMIN", "Admin role for system administration");

            roleRepository.save(patientRole);
            roleRepository.save(doctorRole);
            roleRepository.save(adminRole);

            System.out.println("Default roles created successfully!");
        }
    }

    private void loadSampleHospitals() {
        if (hospitalRepository.count() == 0) {
            Hospital hospital1 = new Hospital(
                "City General Hospital",
                "123 Main Street, New York, NY 10001",
                "Phone: (555) 123-4567, Email: info@citygeneral.com"
            );
            hospital1.setApiEndpoint("https://api.citygeneral.com/medisync");

            Hospital hospital2 = new Hospital(
                "Metropolitan Medical Center",
                "456 Oak Avenue, New York, NY 10002", 
                "Phone: (555) 987-6543, Email: contact@metromedical.com"
            );
            hospital2.setApiEndpoint("https://api.metromedical.com/medisync");

            Hospital hospital3 = new Hospital(
                "St. Mary's Hospital",
                "789 Elm Street, New York, NY 10003",
                "Phone: (555) 456-7890, Email: info@stmarys.com"
            );
            hospital3.setApiEndpoint("https://api.stmarys.com/medisync");

            hospitalRepository.save(hospital1);
            hospitalRepository.save(hospital2);
            hospitalRepository.save(hospital3);

            System.out.println("Sample hospitals created successfully!");
        }
    }
}
