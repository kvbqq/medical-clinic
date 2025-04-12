package com.example.medical_clinic.service;

import com.example.medical_clinic.exception.PatientNotFoundException;
import com.example.medical_clinic.model.ChangePassword;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.repository.PatientRepository;
import com.example.medical_clinic.validation.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email does not exist"));
    }

    public Patient createPatient(Patient patient) {
        PatientValidator.validatePatientCreationData(patientRepository, patient, patient.getEmail());
        return patientRepository.addPatient(patient);
    }

    public void removePatient(String email) {
        if (!patientRepository.removePatient(email)) {
            throw new PatientNotFoundException("Patient with given email does not exist");
        }
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email does not exist"));
        PatientValidator.validatePatientUpdateData(patientRepository, existingPatient, updatedPatient, email);
        existingPatient.update(updatedPatient);

        return existingPatient;
    }

    public Patient changePatientPassword(String email, ChangePassword changePassword) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with given email does not exist"));
        patient.getUser().setPassword(changePassword.getNewPassword());

        return patient;
    }
}
