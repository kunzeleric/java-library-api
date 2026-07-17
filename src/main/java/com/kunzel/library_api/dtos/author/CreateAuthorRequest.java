package com.kunzel.library_api.dtos.author;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public record CreateAuthorRequest(
    @NotBlank String name,
    @NotBlank String nationality,
    @Past LocalDate birthDate) {
}
