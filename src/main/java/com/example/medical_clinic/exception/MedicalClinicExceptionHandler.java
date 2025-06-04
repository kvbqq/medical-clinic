package com.example.medical_clinic.exception;

import com.example.medical_clinic.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MedicalClinicExceptionHandler {
    @ExceptionHandler(MedicalClinicException.class)
    public ResponseEntity<ApiError> handleMedicalClinicException(MedicalClinicException ex) {
        ApiError error = new ApiError(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleUnknown(RuntimeException ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
        return new ResponseEntity<>(error, error.getStatus());
    }
}
