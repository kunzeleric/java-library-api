package com.kunzel.library_api.dtos.author;

import java.time.LocalDate;
import java.util.List;

import com.kunzel.library_api.dtos.book.BookResponse;
import com.kunzel.library_api.model.Author;

public record AuthorDetailResponse(
    Long id,
    String name,
    String nationality,
    LocalDate birthDate,
    List<BookResponse> books) {
  public AuthorDetailResponse(Author author) {
    this(
        author.getId(),
        author.getName(),
        author.getNationality(),
        author.getBirthDate(),
        author.getAuthorBooks().stream()
            .map(BookResponse::new)
            .toList());
  }
}