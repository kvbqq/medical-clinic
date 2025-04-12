package com.example.medical_clinic.mapper;

import com.example.medical_clinic.model.CreatePatientCommand;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.model.PatientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapStructMapper {
    PatientDto toDto(Patient patient);

    Patient toPatientEntity(CreatePatientCommand command);
}
