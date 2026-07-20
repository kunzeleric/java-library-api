package com.kunzel.library_api.dtos.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookRequest(@NotBlank(message = "não deve estar em branco") String title,
                @NotBlank(message = "não deve estar em branco") String isbn,
                @NotNull(message = "deve ser válido") Integer publishedYear,
                @NotBlank(message = "não deve estar em branco") String genre,
                @NotNull(message = "deve ser válido") Long authorId) {

}
