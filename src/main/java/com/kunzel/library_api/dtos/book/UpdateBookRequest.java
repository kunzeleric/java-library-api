package com.kunzel.library_api.dtos.book;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record UpdateBookRequest(@Pattern(regexp = ".*\\S.*", message = "não deve estar em branco") String title,
        @Pattern(regexp = ".*\\S.*", message = "não deve estar em branco") String isbn,
        @Positive(message = "deve ser um valor positivo") Integer publishedYear,
        @Pattern(regexp = ".*\\S.*", message = "não deve estar em branco") String genre, Long authorId) {

}
