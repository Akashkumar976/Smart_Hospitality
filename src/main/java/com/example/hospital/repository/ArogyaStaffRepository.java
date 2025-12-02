package com.example.hospital.repository;

import com.example.hospital.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArogyaStaffRepository extends JpaRepository<Staff, Long> {

    // Find staff by Staff ID
    Optional<Staff> findByStaffId(String staffId);

    // ✅ Check if Aadhaar number already exists
    boolean existsByAdharNumber(String adharNumber);
}
