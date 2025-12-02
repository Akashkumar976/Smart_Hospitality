package com.example.hospital.service;

import com.example.hospital.model.Staff;
import com.example.hospital.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository repo;

    public StaffService(StaffRepository repo) {
        this.repo = repo;
    }

    public List<Staff> getAllStaff() {
        return repo.findAll();
    }

    public Staff getStaffById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    public void saveStaff(Staff staff) {
        repo.save(staff);
    }

    public void deleteStaff(Long id) {
        repo.deleteById(id);
    }

    // Login or Signup using Aadhaar and Staff ID
    public Staff loginOrSignup(String adharNumber, String staffId, String password, String name) {
        Optional<Staff> existingStaff = repo.findByAdharNumberAndStaffId(adharNumber, staffId);

        if (existingStaff.isPresent()) {
            Staff staff = existingStaff.get();

            if (!staff.isPasswordSet()) {
                // Password not set yet, allow setting password
                staff.setPassword(password != null ? password : "");
                staff.setPasswordSet(password != null && !password.isEmpty());
                return repo.save(staff);
            }

            if (staff.getPassword().equals(password)) {
                return staff; // login success
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            // Signup new staff
            Staff newStaff = new Staff();
            newStaff.setAdharNumber(adharNumber);
            newStaff.setStaffId(staffId);
            newStaff.setName(name != null ? name : "Unnamed Staff");
            newStaff.setPassword(password != null ? password : "");
            newStaff.setPasswordSet(password != null && !password.isEmpty());

            return repo.save(newStaff);
        }
    }
}
