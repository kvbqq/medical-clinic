package com.example.medical_clinic.repository;

import com.example.medical_clinic.model.Doctor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    @NonNull
    Page<Doctor> findAll(@NonNull Pageable pageable);
}
