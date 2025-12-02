package com.example.hospital.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "bed_allocation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BedAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;

    private String reason;

    private Timestamp admittedAt;
    private Timestamp dischargedAt;

    private Boolean active;
}
