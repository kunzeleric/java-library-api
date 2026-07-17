package com.kunzel.library_api.dtos.book;

import jakarta.validation.constraints.NotBlank;

public record CreateBookRequest(@NotBlank String title, @NotBlank String isbn, @NotBlank Integer publishedYear,
    @NotBlank String genre, @NotBlank Long authorId) {

}
