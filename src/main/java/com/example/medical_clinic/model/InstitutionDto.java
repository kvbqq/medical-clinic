package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstitutionDto {
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;
}
