package com.example.medical_clinic.validation;

import com.example.medical_clinic.exception.ImmutableFieldException;
import com.example.medical_clinic.exception.InvalidPatientDataException;
import com.example.medical_clinic.exception.PatientAlreadyExistsException;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.repository.PatientJpaRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatientJpaValidator {
    public static void validateImmutableIdCardNo(Patient existingPatient, Patient updatedPatient) {
        if (!existingPatient.getIdCardNo().equals(updatedPatient.getIdCardNo())) {
            throw new ImmutableFieldException("Patient's Card Id Number cannot be changed");
        }
    }

    public static void validateNoNullFields(Patient patient) {
        if (patient.anyNull()) {
            throw new InvalidPatientDataException("Patient cannot have null values");
        }
    }

    public static void validateUniqueEmail(PatientJpaRepository patientRepository, Patient patient, String email) {
        Optional<Patient> optionalPatient = patientRepository.findByEmail(patient.getEmail());
        if (optionalPatient.isPresent() && !optionalPatient.get().getEmail().equals(email)) {
            throw new PatientAlreadyExistsException("Patient with given email already exists");
        }
    }

    public static void validatePatientUpdateData(
            PatientJpaRepository patientRepository,
            Patient existingPatient,
            Patient updatedPatient,
            String email) {
        validateNoNullFields(updatedPatient);
        validateUniqueEmail(patientRepository, updatedPatient, email);
        validateImmutableIdCardNo(existingPatient, updatedPatient);
    }

    public static void validatePatientCreationData(
            PatientJpaRepository patientRepository,
            Patient patient,
            String email) {
        validateNoNullFields(patient);
        validateUniqueEmail(patientRepository, patient, email);
    }
}
