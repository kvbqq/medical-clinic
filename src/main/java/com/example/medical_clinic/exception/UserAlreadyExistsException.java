package com.example.medical_clinic.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends MedicalClinicException {
    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
