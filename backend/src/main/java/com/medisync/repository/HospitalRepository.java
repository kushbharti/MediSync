package com.medisync.repository;

import com.medisync.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Hospital Repository - Data access layer for Hospital entity
 */
@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    
    /**
     * Find hospitals by name containing (case insensitive)
     */
    List<Hospital> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find active hospitals
     */
    List<Hospital> findByIsActive(Boolean isActive);
    
    /**
     * Find hospitals by address containing
     */
    List<Hospital> findByAddressContainingIgnoreCase(String address);
    
    /**
     * Count active hospitals
     */
    @Query("SELECT COUNT(h) FROM Hospital h WHERE h.isActive = true")
    Long countActiveHospitals();
    
    /**
     * Count total hospitals
     */
    @Query("SELECT COUNT(h) FROM Hospital h")
    Long countTotalHospitals();
    
    /**
     * Find hospitals with API endpoints
     */
    @Query("SELECT h FROM Hospital h WHERE h.apiEndpoint IS NOT NULL AND h.apiEndpoint != ''")
    List<Hospital> findHospitalsWithApiEndpoints();
}
