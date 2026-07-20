package com.kunzel.library_api.dtos.book;

public record UpdateBookRequest(String title, String isbn, Integer publishedYear,
    String genre, Long authorId) {

}
