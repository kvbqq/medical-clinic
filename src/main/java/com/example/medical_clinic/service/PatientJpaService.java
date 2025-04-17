package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.PatientNotFoundException;
import com.example.medical_clinic.repository.PatientJpaRepository;
import com.example.medical_clinic.validation.PatientJpaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.medical_clinic.model.Patient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientJpaService {
    private final PatientJpaRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email does not exist"));
    }

    public Patient createPatient(Patient patient) {
        PatientJpaValidator.validatePatientCreationData(patientRepository, patient, patient.getEmail());
        return patientRepository.save(patient);
    }

    public void removePatient(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email does not exist"));
        patientRepository.delete(patient);
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email does not exist"));
        PatientJpaValidator.validatePatientUpdateData(patientRepository, existingPatient, updatedPatient, email);
        existingPatient.update(updatedPatient);

        return patientRepository.save(existingPatient);
    }
}
