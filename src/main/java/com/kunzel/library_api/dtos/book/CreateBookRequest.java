package com.kunzel.library_api.dtos.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookRequest(@NotBlank String title, @NotBlank String isbn, @NotNull Integer publishedYear,
        @NotBlank String genre, @NotNull Long authorId) {

}
