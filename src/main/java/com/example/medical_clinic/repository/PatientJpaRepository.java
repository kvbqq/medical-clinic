package com.example.medical_clinic.repository;

import com.example.medical_clinic.model.Patient;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
}
