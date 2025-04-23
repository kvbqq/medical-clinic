package com.example.medical_clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;
    @ManyToMany(mappedBy = "institutions")
    private List<Doctor> doctors;

    public void update(Institution institution) {
        this.name = institution.getName();
        this.city = institution.getCity();
        this.postalCode = institution.getPostalCode();
        this.street = institution.getStreet();
        this.buildingNumber = institution.getBuildingNumber();
        this.doctors = institution.getDoctors();
    }
}
