package com.example.medical_clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Transient
    private User user;

    public Patient(String email, String idCardNo, String firstName, String lastName, String phoneNumber, LocalDate birthday, User user) {
        this.email = email;
        this.idCardNo = idCardNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.user = user;
    }

    public void update(Patient patient) {
        this.email = patient.getEmail();
        this.idCardNo = patient.getIdCardNo();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.phoneNumber = patient.getPhoneNumber();
        this.birthday = patient.getBirthday();
        this.user = patient.getUser();
    }

    public boolean anyNull() {
        return this.email == null || this.idCardNo == null
                || this.firstName == null || this.lastName == null
                || this.phoneNumber == null || this. birthday == null
                || this.user == null;
    }
}
