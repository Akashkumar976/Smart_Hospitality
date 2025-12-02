package com.example.hospital.controller;

import com.example.hospital.model.Appointment;
import com.example.hospital.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/appointments")
public class AdminAppointmentController {

    private final AppointmentService service;

    public AdminAppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", service.getAllAppointments());
        return "admin-appointments";
    }

    @GetMapping("/add")
    public String addAppointmentForm(@RequestParam(required = false) String date,
                                     @RequestParam(required = false) String doctorName,
                                     Model model) {
        model.addAttribute("appointment", new Appointment());

        LocalDate selectedDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();
        String selectedDoctor = (doctorName != null) ? doctorName : "Dr. Mehta";

        model.addAttribute("availableSlots", service.getAvailableSlots(selectedDoctor, selectedDate));
        return "admin-appointment-form";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        service.saveAppointment(appointment);
        return "redirect:/admin/appointments";
    }

    @GetMapping("/edit/{id}")
    public String editAppointmentForm(@PathVariable Long id, Model model) {
        Appointment appointment = service.getAppointmentById(id);
        model.addAttribute("appointment", appointment);

        String doctorName = appointment.getDoctorName();
        LocalDate date = appointment.getAppointmentDate();

        model.addAttribute("availableSlots", service.getAvailableSlots(doctorName, date));
        return "admin-appointment-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        service.deleteAppointment(id);
        return "redirect:/admin/appointments";
    }
}
