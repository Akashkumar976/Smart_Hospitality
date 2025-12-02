package com.example.hospital.controller;

import com.example.hospital.model.Bed;
import com.example.hospital.service.BedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/beds")
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    // ----------------------------
    // Display all beds
    // ----------------------------
    @GetMapping
    public String listBeds(Model model) {
        model.addAttribute("beds", bedService.getAllBeds());
        return "beds"; // renders beds.html
    }

    // ----------------------------
    // Show form to add a new bed
    // ----------------------------
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("bed", new Bed());
        return "bed_form"; // create a separate bed_form.html for add/edit
    }

    // ----------------------------
    // Save a new bed
    // ----------------------------
    @PostMapping("/save")
    public String saveBed(@ModelAttribute("bed") Bed bed) {
        bedService.saveBed(bed);
        return "redirect:/beds";
    }

    // ----------------------------
    // Edit existing bed
    // ----------------------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Bed bed = bedService.getBedById(id);
        model.addAttribute("bed", bed);
        return "bed_form"; // reuse bed_form.html
    }

    // ----------------------------
    // Delete bed
    // ----------------------------
    @GetMapping("/delete/{id}")
    public String deleteBed(@PathVariable Long id) {
        bedService.deleteBed(id);
        return "redirect:/beds";
    }
}
