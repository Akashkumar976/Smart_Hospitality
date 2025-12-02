package com.example.hospital.service;

import com.example.hospital.model.BedType;
import com.example.hospital.repository.BedTypeRepository;
// import com.example.hospital.service.BedTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedTypeServiceImpl implements BedTypeService {

    private final BedTypeRepository bedTypeRepository;

    public BedTypeServiceImpl(BedTypeRepository bedTypeRepository) {
        this.bedTypeRepository = bedTypeRepository;
    }

    @Override
    public List<BedType> getAllBedTypes() {
        return bedTypeRepository.findAll();
    }

    @Override
    public BedType getBedTypeById(Long id) {
        return bedTypeRepository.findById(id).orElse(null);
    }

    @Override
    public BedType saveBedType(BedType bedType) {
        return bedTypeRepository.save(bedType);
    }

    @Override
    public void deleteBedType(Long id) {
        bedTypeRepository.deleteById(id);
    }
}
