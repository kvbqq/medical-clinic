package com.example.medical_clinic.service;

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
                .orElseThrow(() -> new IllegalArgumentException("Patient with given email does not exist"));
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.addPatient(patient);
    }

    public void removePatient(String email) {
        if (!patientRepository.removePatient(email))
            throw new IllegalArgumentException("Patient with given email does not exist");
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        PatientValidator.validateNoNullFields(updatedPatient);
        PatientValidator.validateUniqueEmail(getAllPatients(), updatedPatient.getEmail());
        Patient existingPatient = getPatient(email);
        PatientValidator.validateImmutableIdCardNo(existingPatient, updatedPatient);
        existingPatient.update(updatedPatient);

        return existingPatient;
    }
}
