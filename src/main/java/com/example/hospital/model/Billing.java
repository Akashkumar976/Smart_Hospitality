package com.example.hospital.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many bills can be linked to one appointment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false)
    private String service;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status; // paid / unpaid

    @Column(nullable = false)
    private String mode;   // cash / online

    private String utr;    // for online payments, can be null for cash

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    // =======================
    // Constructors
    // =======================
    public Billing() {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Billing(Appointment appointment, String patientName, String service,
                   Double amount, String status, String mode, String utr) {
        this.appointment = appointment;
        this.patientName = patientName;
        this.service = service;
        this.amount = amount;
        this.status = status;
        this.mode = mode;
        this.utr = utr;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    // =======================
    // Getters and Setters
    // =======================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getUtr() { return utr; }
    public void setUtr(String utr) { this.utr = utr; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
}
