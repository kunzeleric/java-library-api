package com.kunzel.library_api.dtos.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateBookRequest(@NotBlank(message = "não deve estar em branco") String title,
    @NotBlank(message = "não deve estar em branco") String isbn,
    @Positive(message = "deve ser um valor positivo") @NotNull(message = "deve ser válido") Integer publishedYear,
    @NotBlank(message = "não deve estar em branco") String genre,
    @NotNull(message = "deve ser válido") Long authorId) {

}
