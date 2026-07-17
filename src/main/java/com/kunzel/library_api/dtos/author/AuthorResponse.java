package com.kunzel.library_api.dtos.author;

import com.kunzel.library_api.model.Author;

import java.time.LocalDate;

public record AuthorResponse(Long id, String name, String nationality, LocalDate birthDate) {
  public AuthorResponse(Author author) {
    this(author.getId(), author.getName(), author.getNationality(), author.getBirthDate());
  }
}
