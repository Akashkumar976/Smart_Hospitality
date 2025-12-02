package com.example.hospital.repository;

import com.example.hospital.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {

    // Fetch available beds and eagerly load BedType
    @Query("SELECT b FROM Bed b LEFT JOIN FETCH b.bedType WHERE b.status = 'AVAILABLE'")
    List<Bed> findAvailableBedsWithType();

    // Fetch all beds with BedType eagerly (for displaying in beds.html)
    @Query("SELECT b FROM Bed b LEFT JOIN FETCH b.bedType")
    List<Bed> findAllBedsWithType();
}
