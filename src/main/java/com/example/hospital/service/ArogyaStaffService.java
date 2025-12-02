package com.example.hospital.service;

import com.example.hospital.model.Staff;
import com.example.hospital.repository.ArogyaStaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArogyaStaffService {

    private final ArogyaStaffRepository staffRepository;

    public ArogyaStaffService(ArogyaStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    // Fetch all staff
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // Fetch staff by database ID
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    // Fetch staff by staffId (used for login)
    public Staff getByStaffId(String staffId) {
        return staffRepository.findByStaffId(staffId).orElse(null);
    }

    // ✅ Check if a staff exists by Aadhaar number (for signup validation)
    public boolean existsByAdharNumber(String adharNumber) {
        return staffRepository.existsByAdharNumber(adharNumber);
    }

    // Save or update staff
    public void saveStaff(Staff staff) {
        // Ensure staffId is set
        if (staff.getStaffId() == null || staff.getStaffId().isEmpty()) {
            staff.setStaffId("S" + System.currentTimeMillis());
        }

        // Ensure password is set
        if (staff.getPassword() == null || staff.getPassword().isEmpty()) {
            staff.setPassword("default123");
            staff.setPasswordSet(false);
        } else {
            staff.setPasswordSet(true);
        }

        // Ensure status is set
        if (staff.getStatus() == null || staff.getStatus().isEmpty()) {
            staff.setStatus("Active");
        }

        // Ensure address is not null to avoid SQL errors
        if (staff.getAddress() == null || staff.getAddress().isEmpty()) {
            staff.setAddress("Not Provided");
        }

        // Ensure other essential fields are not null (optional but recommended)
        if (staff.getName() == null) staff.setName("Unknown");
        if (staff.getRole() == null) staff.setRole("Staff");
        if (staff.getMobileNumber() == null) staff.setMobileNumber("0000000000");
        if (staff.getEmail() == null) staff.setEmail("notprovided@example.com");

        staffRepository.save(staff);
    }

    // Delete staff by ID
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
