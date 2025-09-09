package com.medisync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * MediSync - Cross-Hospital Interoperability System
 * Main Spring Boot Application Class
 * 
 * @author MediSync Team
 * @version 1.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class MediSyncApplication {

    public static void main(String[] args) {
        System.out.println("Starting MediSync - Cross-Hospital Interoperability System...");
        SpringApplication.run(MediSyncApplication.class, args);
        System.out.println("MediSync Application Started Successfully!");
    }
}
