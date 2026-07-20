package com.kunzel.library_api.exceptions;

public class BookNotAvailableException extends RuntimeException {
  public BookNotAvailableException(Long id) {
    super("Livro " + id + " não está disponível.");
  }
}
