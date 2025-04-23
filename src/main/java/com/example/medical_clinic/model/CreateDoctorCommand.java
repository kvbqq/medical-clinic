package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDoctorCommand {
    private String email;
    private String firstName;
    private String lastName;
    private String specialization;
    private UserDto user;
}
