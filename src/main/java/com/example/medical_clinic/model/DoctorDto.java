package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DoctorDto {
    private String email;
    private String firstName;
    private String lastName;
    private String specialization;
    private List<Institution> institutions;
}
