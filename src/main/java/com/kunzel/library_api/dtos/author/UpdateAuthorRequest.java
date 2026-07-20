package com.kunzel.library_api.dtos.author;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public record UpdateAuthorRequest(
                @Pattern(regexp = ".*\\S.*", message = "não deve estar em branco") String name,
                @Pattern(regexp = ".*\\S.*", message = "não deve estar em branco") String nationality,
                @Past(message = "deve ser uma data do passado") LocalDate birthDate) {
}
