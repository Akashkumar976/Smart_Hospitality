package com.example.hospital.service;

import com.example.hospital.model.Appointment;
import com.example.hospital.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;
    private static final int SLOT_LIMIT = 25;

    // Define all possible slots
    private static final List<String> SLOTS = Arrays.asList(
            "10:00-10:30", "11:00-11:30", "12:00-12:30", "15:00-15:30"
    );

    public AppointmentService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public List<Appointment> getAllAppointments() {
        return repo.findAllByOrderByAppointmentDateAscSlotAsc();
    }

    public void saveAppointment(Appointment appointment) {
        int count = repo.countByDoctorAndDateAndSlot(
                appointment.getDoctorName(),
                appointment.getAppointmentDate(),
                appointment.getSlot()
        );
        if (count >= SLOT_LIMIT) {
            throw new RuntimeException("Selected slot is full for this day");
        }
        repo.save(appointment);
    }

    // Return available slots for a specific doctor and date
    public List<String> getAvailableSlots(String doctorName, LocalDate date) {
        return SLOTS.stream()
                .filter(slot -> repo.countByDoctorAndDateAndSlot(doctorName, date, slot) < SLOT_LIMIT)
                .collect(Collectors.toList());
    }

    public void deleteAppointment(Long id) {
        repo.deleteById(id);
    }

    public Appointment getAppointmentById(Long id) {
        return repo.findById(id).orElseThrow();
    }
}
