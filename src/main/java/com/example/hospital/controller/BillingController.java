package com.example.hospital.controller;

import com.example.hospital.model.Appointment;
import com.example.hospital.model.Billing;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.service.BillingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin/appointments/billing")
public class BillingController {

    private final BillingService billingService;
    private final AppointmentService appointmentService;

    public BillingController(BillingService billingService, AppointmentService appointmentService) {
        this.billingService = billingService;
        this.appointmentService = appointmentService;
    }

    // =======================
    // View all bills
    // =======================
    @GetMapping
    public String viewBilling(Model model) {
        model.addAttribute("bills", billingService.getAllBills());
        return "billing-list";
    }

    // =======================
    // Add new bill form
    // =======================
    @GetMapping("/add")
    public String addBillingForm(Model model) {
        Billing bill = new Billing();
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("bill", bill);
        model.addAttribute("appointments", appointments);
        return "billing-form"; // Thymeleaf page
    }

    // =======================
    // Save new bill
    // =======================
    @PostMapping("/add")
    public String saveBill(@ModelAttribute Billing bill,
                           @RequestParam("appointmentId") Long appointmentId) {

        // 1. Fetch appointment from DB
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            return "redirect:/admin/appointments/billing/add?error=invalidAppointment";
        }

        // 2. Assign appointment to billing
        bill.setAppointment(appointment);

        // 3. Ensure date and time are set
        if (bill.getDate() == null) bill.setDate(LocalDate.now());
        if (bill.getTime() == null) bill.setTime(LocalTime.now());

        // 4. Save bill
        billingService.saveBill(bill);

        return "redirect:/admin/appointments/billing";
    }

    // =======================
    // Edit bill form
    // =======================
    @GetMapping("/edit/{id}")
    public String editBillingForm(@PathVariable Long id, Model model) {
        Billing bill = billingService.getBillById(id);
        if (bill == null) return "redirect:/admin/appointments/billing";

        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("bill", bill);
        model.addAttribute("appointments", appointments);
        return "billing-form"; // reuse form
    }

    // =======================
    // Update existing bill
    // =======================
    @PostMapping("/edit/{id}")
    public String updateBill(@PathVariable Long id,
                             @RequestParam("appointmentId") Long appointmentId,
                             @ModelAttribute Billing updatedBill) {

        Billing bill = billingService.getBillById(id);
        if (bill == null) return "redirect:/admin/appointments/billing";

        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) return "redirect:/admin/appointments/billing?error=invalidAppointment";

        bill.setAppointment(appointment);

        bill.setPatientName(updatedBill.getPatientName());
        bill.setService(updatedBill.getService());
        bill.setAmount(updatedBill.getAmount());
        bill.setStatus(updatedBill.getStatus());
        bill.setMode(updatedBill.getMode());
        bill.setUtr(updatedBill.getUtr());

        bill.setDate(updatedBill.getDate() != null ? updatedBill.getDate() : LocalDate.now());
        bill.setTime(updatedBill.getTime() != null ? updatedBill.getTime() : LocalTime.now());

        billingService.saveBill(bill);

        return "redirect:/admin/appointments/billing";
    }

    // =======================
    // Delete bill
    // =======================
    @GetMapping("/delete/{id}")
    public String deleteBill(@PathVariable Long id) {
        billingService.deleteBill(id);
        return "redirect:/admin/appointments/billing";
    }
}
