package com.example.medical_clinic.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Patient {
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
}
