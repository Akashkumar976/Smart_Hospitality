package com.example.hospital.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_uid", nullable = false, unique = true)
    private String patientUid;

    private String name;

    @Column(name = "mobile_number")
    private String mobileNumber;

    private String address;
    private String gender;
    private Integer age;

    private String status;   // NEW | ADMITTED | DISCHARGED
}
