package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VisitDto {
    private LocalDateTime startVisitDate;
    private LocalDateTime endVisitDate;
    private PatientDto patient;
    private DoctorDto doctor;
}
