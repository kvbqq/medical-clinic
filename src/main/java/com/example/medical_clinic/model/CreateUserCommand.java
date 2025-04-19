package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserCommand {
    private String username;
    private String password;
}
