package com.example.medical_clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@Table(name = "INSTITUTIONS")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institution institution = (Institution) o;
        return id != null && id.equals(institution.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
