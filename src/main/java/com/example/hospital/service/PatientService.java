package com.example.hospital.service;

import com.example.hospital.model.Patient;
import com.example.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public Patient createPatient(Patient p) {
        p.setPatientUid("PAT-" + UUID.randomUUID().toString().substring(0, 8));
        p.setStatus("NEW"); 
        return repo.save(p);
    }

    // GET ALL
    public List<Patient> getAllPatients() {
        return repo.findAll();
    }

    // GET BY ID
    public Patient getPatientById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
    }

    // UPDATE
    public Patient updatePatient(Long id, Patient data) {
        Patient p = getPatientById(id);

        p.setName(data.getName());
        p.setMobileNumber(data.getMobileNumber());
        p.setAddress(data.getAddress());
        p.setGender(data.getGender());
        p.setAge(data.getAge());
        p.setStatus(data.getStatus());

        return repo.save(p);
    }

    // DELETE
    public void deletePatient(Long id) {
        Patient p = getPatientById(id);
        repo.delete(p);
    }
}
