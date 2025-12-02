package com.example.hospital.service;

import com.example.hospital.model.Bed;
import com.example.hospital.repository.BedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedService {

    private final BedRepository bedRepository;

    public BedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    public List<Bed> getAllBeds() {
        return bedRepository.findAllBedsWithType();
    }

    public Bed getBedById(Long id) {
        return bedRepository.findById(id).orElse(null);
    }

    public void saveBed(Bed bed) {
        bedRepository.save(bed);
    }

    public void deleteBed(Long id) {
        bedRepository.deleteById(id);
    }
}
