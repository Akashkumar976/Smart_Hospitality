package com.example.hospital.controller;

// import com.example.hospital.dto.BedAllocationRequest;
import com.example.hospital.model.BedAllocation;
import com.example.hospital.service.BedAllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beds")
public class BedAllocationController {

    private final BedAllocationService service;

    public BedAllocationController(BedAllocationService service) {
        this.service = service;
    }

    // ---------------------------
    // Allocate a bed to a patient
    // ---------------------------
@PostMapping("/allocate")
public String allocateBed(
        @RequestParam Long patientId,
        @RequestParam Long bedId,
        @RequestParam String reason
) {

    service.allocate(patientId, bedId, reason);

    return "redirect:/patients";  // redirect where you want after success
}

    // ---------------------------
    // Discharge patient & free bed
    // ---------------------------
    @PutMapping("/discharge/{allocationId}")
    public ResponseEntity<BedAllocation> discharge(
            @PathVariable Long allocationId
    ) {
        BedAllocation allocation = service.discharge(allocationId);
        return ResponseEntity.ok(allocation);
    }
}
