package com.example.hospital.service;

import com.example.hospital.model.BedType;
import java.util.List;

public interface BedTypeService {
    List<BedType> getAllBedTypes();
    BedType getBedTypeById(Long id);
    BedType saveBedType(BedType bedType);
    void deleteBedType(Long id);
}
