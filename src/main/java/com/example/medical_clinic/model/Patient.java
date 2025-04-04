package com.example.medical_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Arrays;

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
        return Arrays.stream(this.getClass().getDeclaredFields())
                .anyMatch(field -> {
                    try {
                        return field.get(this) == null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
