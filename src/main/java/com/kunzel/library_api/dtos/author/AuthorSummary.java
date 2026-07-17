package com.kunzel.library_api.dtos.author;

import java.time.LocalDate;

import com.kunzel.library_api.model.Author;

public record AuthorSummary(Long id, String name, String nationality, LocalDate birthDate) {
  public AuthorSummary(Author author) {
    this(author.getId(), author.getName(), author.getNationality(), author.getBirthDate());
  }
}
