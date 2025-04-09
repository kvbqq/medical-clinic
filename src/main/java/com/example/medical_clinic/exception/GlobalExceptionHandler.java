package com.example.medical_clinic.exception;

import com.example.medical_clinic.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiError> handlePatientNotFound(PatientNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleDuplicatePatient(PatientAlreadyExistsException ex) {
        ApiError error = new ApiError(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(InvalidPatientDataException.class)
    public ResponseEntity<ApiError> handleInvalidData(InvalidPatientDataException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ImmutableFieldException.class)
    public ResponseEntity<ApiError> handleImmutableField(ImmutableFieldException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknown(Exception ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
        return new ResponseEntity<>(error, error.getStatus());
    }
}
