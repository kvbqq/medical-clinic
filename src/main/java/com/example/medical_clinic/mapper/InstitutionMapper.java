package com.example.medical_clinic.mapper;

import com.example.medical_clinic.model.CreateInstitutionCommand;
import com.example.medical_clinic.model.Institution;
import com.example.medical_clinic.model.InstitutionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    InstitutionDto toDto(Institution institution);
    Institution toInstitutionEntity(CreateInstitutionCommand command);
}
