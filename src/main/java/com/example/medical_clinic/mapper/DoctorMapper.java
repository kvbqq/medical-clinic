package com.example.medical_clinic.mapper;

import com.example.medical_clinic.model.CreateDoctorCommand;
import com.example.medical_clinic.model.Doctor;
import com.example.medical_clinic.model.DoctorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);

    Doctor toDoctorEntity(CreateDoctorCommand command);
}
