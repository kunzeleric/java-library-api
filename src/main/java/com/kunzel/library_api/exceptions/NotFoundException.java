package com.kunzel.library_api.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(Long id) {
    super("Recurso não encontrado: " + id);
  }
}
