package com.example.medical_clinic.validation;

import com.example.medical_clinic.model.Patient;
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

    public static void validateUniqueEmail(List<Patient> patients, String email) {
        if (!patients.isEmpty() && patients.stream()
                .map(Patient::getEmail)
                .anyMatch(existingEmail -> existingEmail.equals(email)))
            throw new IllegalArgumentException("Patient with given email already exists");
    }
}
