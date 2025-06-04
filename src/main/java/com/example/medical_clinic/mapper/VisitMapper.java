package com.example.medical_clinic.mapper;

import com.example.medical_clinic.model.CreateVisitCommand;
import com.example.medical_clinic.model.Visit;
import com.example.medical_clinic.model.VisitDto;
import com.example.medical_clinic.service.DoctorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DoctorService.class})
public interface VisitMapper {
    VisitDto toDto(Visit visit);

    @Mapping(target = "doctor", source = "doctorId")
    Visit toVisitEntity(CreateVisitCommand command);
}
