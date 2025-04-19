package com.example.medical_clinic.repository;

import com.example.medical_clinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
}
