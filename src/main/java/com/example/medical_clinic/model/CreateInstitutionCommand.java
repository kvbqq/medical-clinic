package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateInstitutionCommand {
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;
    private List<Doctor> doctors;
}
