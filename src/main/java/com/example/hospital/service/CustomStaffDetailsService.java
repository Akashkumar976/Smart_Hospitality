package com.example.hospital.service;

import com.example.hospital.model.Staff;
import com.example.hospital.repository.StaffRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomStaffDetailsService implements UserDetailsService {

    private final StaffRepository staffRepository;

    public CustomStaffDetailsService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String staffId) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByStaffId(staffId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Staff ID: " + staffId));

        String role = (staff.getRole() != null) ? staff.getRole().toUpperCase() : "STAFF";

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        return new User(staff.getStaffId(), staff.getPassword(), Collections.singletonList(authority));
    }
}
