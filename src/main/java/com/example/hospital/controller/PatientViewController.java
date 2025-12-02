package com.example.hospital.controller;

import com.example.hospital.model.Patient;
import com.example.hospital.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientViewController {

    private final PatientService service;

    public PatientViewController(PatientService service) {
        this.service = service;
    }

    // List all patients
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", service.getAllPatients());
        return "patients";  // patients.html
    }

    // Show add patient form
    @GetMapping("/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients-add"; // patients-add.html
    }

    // Save new patient
    @PostMapping("/add")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        service.createPatient(patient);
        return "redirect:/patients";
    }

    // Show edit patient form
    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        Patient p = service.getPatientById(id);
        model.addAttribute("patient", p); // Important for Thymeleaf th:object
        return "patients-edit"; // patients-edit.html
    }

    // Update patient
    @PostMapping("/edit/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute("patient") Patient patient) {
        service.updatePatient(id, patient);
        return "redirect:/patients";
    }

    // Delete patient
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
        return "redirect:/patients";
    }
}
