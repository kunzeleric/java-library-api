package com.kunzel.library_api.dtos.author;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;

public record UpdateAuthorRequest(
        String name,
        String nationality,
        @Past(message = "deve ser uma data do passado") LocalDate birthDate) {
}
