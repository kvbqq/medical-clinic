package com.example.medical_clinic.mapper;

import com.example.medical_clinic.model.CreateVisitCommand;
import com.example.medical_clinic.model.Visit;
import com.example.medical_clinic.model.VisitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    VisitDto toDto(Visit visit);

    Visit toVisitEntity(CreateVisitCommand command);
}
