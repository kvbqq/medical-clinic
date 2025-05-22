package com.example.medical_clinic.repository;

import com.example.medical_clinic.model.Doctor;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.model.Visit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByPatient(Patient patient, Pageable pageable);

    Optional<Visit> findByDoctor(Doctor doctor, Pageable pageable);
}
