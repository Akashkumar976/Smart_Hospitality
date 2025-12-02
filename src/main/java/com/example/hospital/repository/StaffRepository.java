package com.example.hospital.repository;

import com.example.hospital.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByAdharNumber(String adharNumber);

    boolean existsByAdharNumber(String adharNumber);

    Optional<Staff> findByStaffId(String staffId);

    boolean existsByStaffId(String staffId);

    Optional<Staff> findByAdharNumberAndStaffId(String adharNumber, String staffId);
}
