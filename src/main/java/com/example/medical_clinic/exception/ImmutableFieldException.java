package com.example.medical_clinic.exception;

import org.springframework.http.HttpStatus;

public class ImmutableFieldException extends MedicalClinicException {
    public ImmutableFieldException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
