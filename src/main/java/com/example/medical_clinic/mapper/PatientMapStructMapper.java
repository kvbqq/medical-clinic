package com.example.medical_clinic.mapper;

import com.example.medical_clinic.model.CreatePatientCommand;
import com.example.medical_clinic.model.Patient;
import com.example.medical_clinic.model.PatientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapStructMapper {
    @Mapping(source="user.username", target="username")
    PatientDto toDto(Patient patient);

    Patient toPatientEntity(CreatePatientCommand command);
}
