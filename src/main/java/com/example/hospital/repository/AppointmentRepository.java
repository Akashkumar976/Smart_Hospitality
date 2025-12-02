package com.example.hospital.repository;

import com.example.hospital.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Count appointments for a specific doctor, date, and slot
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.appointmentDate = :date AND a.slot = :slot AND a.doctorName = :doctorName")
    int countByDoctorAndDateAndSlot(@Param("doctorName") String doctorName,
                                    @Param("date") LocalDate date,
                                    @Param("slot") String slot);

    // Find all appointments ordered by date and slot
    List<Appointment> findAllByOrderByAppointmentDateAscSlotAsc();
}
