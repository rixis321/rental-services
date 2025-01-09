package com.example.rentalservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    private final String fieldName;
    public ValidationException(String message) {
        super(message);
        this.fieldName = message;
    }

    public String getFieldName() {
        return fieldName;
    }
}
