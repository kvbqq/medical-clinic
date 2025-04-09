package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Patient {
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    public void update(Patient patient) {
        this.email = patient.getEmail();
        this.password = patient.getPassword();
        this.idCardNo = patient.getIdCardNo();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.phoneNumber = patient.getPhoneNumber();
        this.birthday = patient.getBirthday();
    }

    public boolean anyNull() {
        return this.email == null || this.password == null
                || this.idCardNo == null || this.firstName == null
                || this.lastName == null || this.phoneNumber == null
                || this. birthday == null;
    }
}
