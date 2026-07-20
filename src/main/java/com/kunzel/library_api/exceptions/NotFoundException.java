package com.kunzel.library_api.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(Long id, String resource) {
    super("Recurso do tipo " + resource + " não encontrado: " + id);
  }
}
