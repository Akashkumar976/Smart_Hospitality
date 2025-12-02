package com.example.hospital.repository;

import com.example.hospital.model.BedAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedAllocationRepository extends JpaRepository<BedAllocation, Long> {

    // Find active allocation for a specific bed
    BedAllocation findByBedIdAndActive(Long bedId, Boolean active);

    // Find active allocation for a specific patient
    BedAllocation findByPatientIdAndActive(Long patientId, Boolean active);
}
