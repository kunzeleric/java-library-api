package com.kunzel.library_api.dtos.book;

import com.kunzel.library_api.model.Author;
import com.kunzel.library_api.model.Book;

public record BookResponse(Long id, String title, String isbn, int publishedYear, String genre, boolean available,
    Author author) {
  public BookResponse(Book book) {
    this(
        book.getId(),
        book.getTitle(),
        book.getIsbn(),
        book.getPublishedYear(),
        book.getGenre(),
        book.isAvailable(),
        book.getAuthor());
  }

}
