package com.example.medical_clinic.exception;

public class ImmutableFieldException extends RuntimeException {
    public ImmutableFieldException(String message) {
        super(message);
    }
}
