package com.kunzel.library_api.dtos.author;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record CreateAuthorRequest(
        @NotBlank(message = "não deve estar em branco") String name,
        @NotBlank(message = "não deve estar em branco") String nationality,
        @NotNull(message = "deve ser válido") @Past(message = "deve ser uma data do passado") LocalDate birthDate) {
}
