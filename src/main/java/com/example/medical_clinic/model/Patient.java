package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private String email;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
    private User user;

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
