package com.example.medical_clinic.exception;

import org.springframework.http.HttpStatus;

public class InvalidPatientDataException extends MedicalClinicException {
    public InvalidPatientDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
