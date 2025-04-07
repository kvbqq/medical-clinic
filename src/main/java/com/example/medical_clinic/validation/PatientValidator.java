package com.example.medical_clinic.validation;

import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.repository.PatientRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {
    public static void validateImmutableIdCardNo(Patient existingPatient, Patient updatedPatient) {
        if (!existingPatient.getIdCardNo().equals(updatedPatient.getIdCardNo()))
            throw new IllegalArgumentException("Patient's Card Id Number cannot be changed");
    }

    public static void validateNoNullFields(Patient patient) {
        if (patient.anyNull())
            throw new IllegalArgumentException("Patient cannot have null values");
    }

    public static void validateUniqueEmail(PatientRepository patientRepository, Patient patient, String email) {
        List<Patient> patients = patientRepository.findAll();
        patients.remove(patient);
        if (!patients.isEmpty() && patients.stream()
                .map(Patient::getEmail)
                .anyMatch(existingEmail -> existingEmail.equals(email)))
            throw new IllegalArgumentException("Patient with given email already exists");
    }

    public static void validatePatientUpdateData(
            PatientRepository patientRepository,
            Patient existingPatient,
            Patient updatedPatient,
            String email) {
        validateNoNullFields(updatedPatient);
        validateUniqueEmail(patientRepository, existingPatient, email);
        validateImmutableIdCardNo(existingPatient, updatedPatient);
    }

    public static void validatePatientCreationData(
            PatientRepository patientRepository,
            Patient patient,
            String email) {
        validateNoNullFields(patient);
        validateUniqueEmail(patientRepository, patient, email);
    }
}
