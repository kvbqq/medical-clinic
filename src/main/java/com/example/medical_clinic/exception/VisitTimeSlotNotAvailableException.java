package com.example.medical_clinic.exception;

import org.springframework.http.HttpStatus;

public class VisitTimeSlotNotAvailableException extends MedicalClinicException {
    public VisitTimeSlotNotAvailableException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
