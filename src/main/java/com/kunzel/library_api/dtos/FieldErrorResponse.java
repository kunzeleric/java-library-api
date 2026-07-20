package com.kunzel.library_api.dtos;

import org.springframework.validation.FieldError;

public record FieldErrorResponse(String campo, String mensagem) {
    public FieldErrorResponse(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
