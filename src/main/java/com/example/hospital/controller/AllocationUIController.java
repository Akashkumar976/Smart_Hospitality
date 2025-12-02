package com.example.hospital.controller;

import com.example.hospital.model.Bed;
import com.example.hospital.model.Patient;
import com.example.hospital.repository.BedRepository;
import com.example.hospital.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/allocations")
public class AllocationUIController {

    private final PatientRepository patientRepo;
    private final BedRepository bedRepo;

    public AllocationUIController(PatientRepository patientRepo, BedRepository bedRepo) {
        this.patientRepo = patientRepo;
        this.bedRepo = bedRepo;
    }

    @GetMapping("/allocate/{patientId}")
    public String showAllocationPage(@PathVariable Long patientId, Model model) {

        // Fetch patient
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Fetch AVAILABLE beds with BedType loaded
        List<Bed> availableBeds = bedRepo.findAvailableBedsWithType();

        // Debug output
        System.out.println("Patient: " + patient.getId() + " - " + patient.getName());
        System.out.println("Number of available beds: " + availableBeds.size());
        for (Bed b : availableBeds) {
            System.out.println("Bed: " + (b.getCode() != null ? b.getCode() : "No Code") + 
                               " - " + (b.getBedType() != null ? b.getBedType().getName() : "No Type") + 
                               " - " + (b.getWard() != null ? b.getWard() : "No Ward"));
        }

        model.addAttribute("patient", patient);
        model.addAttribute("beds", availableBeds);

        return "allocate"; // loads allocate.html
    }
}
