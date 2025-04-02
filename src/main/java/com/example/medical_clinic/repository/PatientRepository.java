package com.example.medical_clinic.repository;

import com.example.medical_clinic.model.Patient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Getter
public class PatientRepository {
    private final List<Patient> patients;

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Patient addPatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public Patient removePatient(Patient patient) {
        patients.remove(patient);
        return patient;
    }
}
