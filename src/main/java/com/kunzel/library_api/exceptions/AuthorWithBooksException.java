package com.kunzel.library_api.exceptions;

public class AuthorWithBooksException extends RuntimeException {
  public AuthorWithBooksException(Long id) {
    super("Não é possível deletar autor de ID" + id + " pois ele possui livros cadastrados.");
  }
}
