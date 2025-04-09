package com.example.medical_clinic.mapper;

import com.example.medical_clinic.model.CreatePatientCommand;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.model.PatientDto;

public class PatientMapper {
    public static PatientDto toDto(Patient patient) {
        return new PatientDto(
                patient.getEmail(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getBirthday()
        );
    }

    public static Patient toPatientEntity(CreatePatientCommand command) {
        return new Patient(
                command.getEmail(),
                command.getPassword(),
                command.getIdCardNo(),
                command.getFirstName(),
                command.getLastName(),
                command.getPhoneNumber(),
                command.getBirthday()
        );
    }
}
