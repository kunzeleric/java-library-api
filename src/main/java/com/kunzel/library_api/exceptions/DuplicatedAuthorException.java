package com.kunzel.library_api.exceptions;

public class DuplicatedAuthorException extends RuntimeException {
  public DuplicatedAuthorException(Long existingAuthorId, String authorName) {
    super("Autor com nome " + authorName + " já existe sob registro do ID " + existingAuthorId + ".");
  }
}
