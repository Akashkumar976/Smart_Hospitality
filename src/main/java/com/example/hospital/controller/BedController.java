package com.example.hospital.controller;

import com.example.hospital.model.Bed;
import com.example.hospital.service.BedService;
import com.example.hospital.service.BedTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/beds")
public class BedController {

    private final BedService bedService;
    private final BedTypeService bedTypeService;

    public BedController(BedService bedService, BedTypeService bedTypeService) {
        this.bedService = bedService;
        this.bedTypeService = bedTypeService;
    }

    // ----------------------------
    // Display all beds
    // ----------------------------
    @GetMapping
    public String listBeds(Model model) {
        model.addAttribute("beds", bedService.getAllBeds());
        return "beds"; // beds.html
    }

    // ----------------------------
    // Add Bed Form
    // ----------------------------
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("bed", new Bed());
        model.addAttribute("bedTypes", bedTypeService.getAllBedTypes());
        return "bed_form"; // Corrected template name
    }

    // ----------------------------
    // Save Bed
    // ----------------------------
    @PostMapping("/save")
    public String saveBed(@ModelAttribute("bed") Bed bed) {
        bedService.saveBed(bed);
        return "redirect:/beds";
    }

    // ----------------------------
    // Edit Bed Form
    // ----------------------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Bed bed = bedService.getBedById(id);
        model.addAttribute("bed", bed);
        model.addAttribute("bedTypes", bedTypeService.getAllBedTypes());
        return "bed_form"; // Corrected template name
    }

    // ----------------------------
    // Delete Bed
    // ----------------------------
    @GetMapping("/delete/{id}")
    public String deleteBed(@PathVariable Long id) {
        bedService.deleteBed(id);
        return "redirect:/beds";
    }
}
