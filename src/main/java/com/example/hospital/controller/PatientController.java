package com.example.hospital.controller;

import com.example.hospital.model.Patient;
import com.example.hospital.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping("/create")
    public Patient createPatient(@RequestBody Patient p) {
        return service.createPatient(p);
    }

    // GET ALL
    @GetMapping("/all")
    public List<Patient> getAllPatients() {
        return service.getAllPatients();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return service.getPatientById(id);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient p) {
        return service.updatePatient(id, p);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
        return "Patient deleted successfully!";
    }
}
