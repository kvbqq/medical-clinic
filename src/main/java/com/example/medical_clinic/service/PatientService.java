package com.example.medical_clinic.service;

import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.getPatients();
    }

    public Patient getPatient(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient with given email does not exist"));
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.addPatient(patient);
    }

    public Patient removePatient(String email) {
        return patientRepository.removePatient(getPatient(email));
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        Patient existingPatient = getPatient(email);

        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setPassword(updatedPatient.getPassword());
        existingPatient.setIdCardNo(updatedPatient.getIdCardNo());
        existingPatient.setFirstName(updatedPatient.getFirstName());
        existingPatient.setLastName(updatedPatient.getLastName());
        existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
        existingPatient.setBirthday(updatedPatient.getBirthday());

        return existingPatient;
    }
}
