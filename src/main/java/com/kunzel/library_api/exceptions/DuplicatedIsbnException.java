package com.kunzel.library_api.exceptions;

public class DuplicatedIsbnException extends RuntimeException {
  public DuplicatedIsbnException(String isbn) {
    super("Código ISBN " + isbn + " já existe na base de dados de livros. Utilize outro código.");
  }
}
