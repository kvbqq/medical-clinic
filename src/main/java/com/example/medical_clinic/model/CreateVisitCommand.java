package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateVisitCommand {
    private LocalDateTime startVisitDate;
    private LocalDateTime endVisitDate;
    private Long doctorId;
}
