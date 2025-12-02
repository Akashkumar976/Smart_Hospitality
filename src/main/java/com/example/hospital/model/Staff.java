package com.example.hospital.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "arogya_staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staff_id", nullable = false, unique = true)
    private String staffId;

    private String name;

    @Column(name = "adhar_number")
    private String adharNumber;

    private String gender;

    @Column(name = "join_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime joinDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "mobile_number")
    private String mobileNumber;

    private String role;
    private String status;

    @Column(nullable = false)
    private String password;

    @Column(name = "password_set")
    private boolean passwordSet;

    private String email;
    private String address;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAdharNumber() { return adharNumber; }
    public void setAdharNumber(String adharNumber) { this.adharNumber = adharNumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDateTime getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDateTime joinDate) { this.joinDate = joinDate; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isPasswordSet() { return passwordSet; }
    public void setPasswordSet(boolean passwordSet) { this.passwordSet = passwordSet; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
