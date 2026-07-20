package com.kunzel.library_api.dtos;

import org.springframework.validation.FieldError;

public record FieldErrorResponse(String fieldName, String message) {
    public FieldErrorResponse(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
