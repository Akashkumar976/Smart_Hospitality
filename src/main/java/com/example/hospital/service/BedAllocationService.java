package com.example.hospital.service;

import com.example.hospital.model.Bed;
import com.example.hospital.model.BedAllocation;
import com.example.hospital.model.Patient;
import com.example.hospital.repository.BedAllocationRepository;
import com.example.hospital.repository.BedRepository;
import com.example.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class BedAllocationService {

    private final PatientRepository patientRepo;
    private final BedRepository bedRepo;
    private final BedAllocationRepository allocationRepo;

    public BedAllocationService(PatientRepository patientRepo,
                                BedRepository bedRepo,
                                BedAllocationRepository allocationRepo) {
        this.patientRepo = patientRepo;
        this.bedRepo = bedRepo;
        this.allocationRepo = allocationRepo;
    }

    // --------------------------------------------------
    // Allocate a bed to a patient
    // --------------------------------------------------
    public BedAllocation allocate(Long patientId, Long bedId, String reason) {

        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Bed bed = bedRepo.findById(bedId)
                .orElseThrow(() -> new RuntimeException("Bed not found"));

        // Prevent allocating an OCCUPIED bed
        if (bed.getStatus().equals("OCCUPIED")) {
            throw new RuntimeException("Bed is already occupied");
        }

        // Prevent double allocation to patient
        BedAllocation existing = allocationRepo.findByPatientIdAndActive(patientId, true);
        if (existing != null) {
            throw new RuntimeException("Patient already has an allocated bed");
        }

        // Update bed status
        bed.setStatus("OCCUPIED");
        bedRepo.save(bed);

        // Update patient status
        patient.setStatus("ADMITTED");
        patientRepo.save(patient);

        // Save allocation
        BedAllocation alloc = new BedAllocation();
        alloc.setPatient(patient);
        alloc.setBed(bed);
        alloc.setReason(reason);
        alloc.setAdmittedAt(Timestamp.from(Instant.now()));
        alloc.setActive(true);

        return allocationRepo.save(alloc);
    }

    // --------------------------------------------------
    // Discharge patient & free bed
    // --------------------------------------------------
    public BedAllocation discharge(Long allocationId) {

        BedAllocation alloc = allocationRepo.findById(allocationId)
                .orElseThrow(() -> new RuntimeException("Allocation not found"));

        if (!alloc.getActive()) {
            throw new RuntimeException("Patient is already discharged");
        }

        alloc.setActive(false);
        alloc.setDischargedAt(Timestamp.from(Instant.now()));

        // Free the bed
        Bed bed = alloc.getBed();
        bed.setStatus("AVAILABLE");
        bedRepo.save(bed);

        // Update patient
        Patient patient = alloc.getPatient();
        patient.setStatus("DISCHARGED");
        patientRepo.save(patient);

        return allocationRepo.save(alloc);
    }
}
