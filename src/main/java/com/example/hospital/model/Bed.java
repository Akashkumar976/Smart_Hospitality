package com.example.hospital.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bed")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bed_number")  // Map to the actual DB column
    private String code;  // Unique bed code like G-101, ICU-01

    @ManyToOne
    @JoinColumn(name = "bed_type_id")  // Foreign key to bed_type table
    private BedType bedType;

    @Column(name = "room_number")
    private String roomNumber;

    private String ward;

    private String status;  // AVAILABLE or OCCUPIED
    private String notes;
}
