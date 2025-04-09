package com.example.medical_clinic.repository;

import com.example.medical_clinic.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {
    private final List<Patient> patients;

    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Patient addPatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public Boolean removePatient(String email) {
        return patients.removeIf(patient -> patient.getEmail().equals(email));
    }
}
