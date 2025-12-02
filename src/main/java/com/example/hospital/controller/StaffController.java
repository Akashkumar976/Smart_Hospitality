package com.example.hospital.controller;

import com.example.hospital.model.Staff;
import com.example.hospital.service.ArogyaStaffService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/staff")
public class StaffController {

    private final ArogyaStaffService staffService;
    private final PasswordEncoder passwordEncoder;

    public StaffController(ArogyaStaffService staffService, PasswordEncoder passwordEncoder) {
        this.staffService = staffService;
        this.passwordEncoder = passwordEncoder;
    }

    // -------------------- LOGIN PAGE --------------------
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid Staff ID or Password");
        if (logout != null) model.addAttribute("msg", "You have been logged out successfully.");
        return "arogya-staff-login";
    }

    // -------------------- SIGNUP PAGE --------------------
    @PostMapping("/signup")
    public String signup(@RequestParam String adharNumber,
                         @RequestParam String staffId,
                         @RequestParam String name,
                         @RequestParam String password,
                         Model model) {

        // Aadhaar already exists?
        if (staffService.existsByAdharNumber(adharNumber)) {
            model.addAttribute("error", "Aadhaar already registered!");
            return "arogya-staff-login";
        }

        // StaffId already exists?
        if (staffService.getByStaffId(staffId) != null) {
            model.addAttribute("error", "Staff ID already taken!");
            return "arogya-staff-login";
        }

        // Create and save new staff
        Staff staff = new Staff();
        staff.setAdharNumber(adharNumber);
        staff.setStaffId(staffId);
        staff.setName(name);
        staff.setPassword(passwordEncoder.encode(password));
        staff.setStatus("Active");
        staff.setRole("STAFF");
        staffService.saveStaff(staff);

        model.addAttribute("msg", "Signup successful! Please login.");
        return "arogya-staff-login";
    }

    // -------------------- DASHBOARD (ROLE-BASED ACCESS) --------------------
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        List<Staff> staffList = staffService.getAllStaff();
        model.addAttribute("staffList", staffList);
        return "arogya-staff-dashboard"; // Thymeleaf view for admin/staff list
    }

    // -------------------- PERSONAL DASHBOARD --------------------
    @GetMapping("/dashboard/staff/{id}")
    public String staffDashboard(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id);
        if (staff == null) {
            return "redirect:/admin/staff/login";
        }
        model.addAttribute("staff", staff);
        return "staff-dashboard"; // Create staff-dashboard.html
    }

    // -------------------- VIEW STAFF (ADMIN) --------------------
    @GetMapping("/view/{id}")
    public String viewStaff(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id);
        if (staff == null) return "redirect:/admin/staff/dashboard";
        model.addAttribute("staff", staff);
        return "arogya-staff-view";
    }

    // -------------------- ADD STAFF --------------------
    @GetMapping("/add")
    public String addStaffForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "admin-staff-form";
    }

    @PostMapping("/add")
    public String addStaff(@ModelAttribute Staff staff) {
        if (staff.getPassword() == null || staff.getPassword().isEmpty()) {
            staff.setPassword(passwordEncoder.encode("default123"));
        } else {
            staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        }

        if (staff.getRole() == null) staff.setRole("STAFF");
        if (staff.getStatus() == null) staff.setStatus("Active");
        staffService.saveStaff(staff);
        return "redirect:/admin/staff/dashboard";
    }

    // -------------------- EDIT STAFF --------------------
    @GetMapping("/edit/{id}")
    public String editStaffForm(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id);
        if (staff == null) return "redirect:/admin/staff/dashboard";
        model.addAttribute("staff", staff);
        return "admin-staff-form";
    }

    @PostMapping("/edit/{id}")
    public String editStaff(@PathVariable Long id, @ModelAttribute Staff updatedStaff) {
        Staff existing = staffService.getStaffById(id);
        if (existing == null) return "redirect:/admin/staff/dashboard";

        existing.setName(updatedStaff.getName());
        existing.setAdharNumber(updatedStaff.getAdharNumber());
        existing.setAddress(updatedStaff.getAddress());
        existing.setMobileNumber(updatedStaff.getMobileNumber());
        existing.setRole(updatedStaff.getRole());
        existing.setStatus(updatedStaff.getStatus());

        if (updatedStaff.getPassword() != null && !updatedStaff.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(updatedStaff.getPassword()));
        }

        staffService.saveStaff(existing);
        return "redirect:/admin/staff/dashboard";
    }

    // -------------------- DELETE STAFF --------------------
    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/admin/staff/dashboard";
    }

    // -------------------- LOGOUT --------------------
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/staff/login?logout";
    }
}
