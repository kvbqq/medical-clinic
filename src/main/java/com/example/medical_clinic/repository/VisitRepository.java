package com.example.medical_clinic.repository;

import com.example.medical_clinic.model.Doctor;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.model.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Page<Visit> findByPatient(Patient patient, Pageable pageable);

    Page<Visit> findByDoctor(Doctor doctor, Pageable pageable);

    @Query("""
            SELECT v FROM Visit v
            WHERE v.doctor.id = :doctorId
            AND v.startVisitDate < :endTime
            AND v.endVisitDate > :startTime
            """)
    List<Visit> findOverlappingVisits(
            @Param("doctorId") Long doctorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
