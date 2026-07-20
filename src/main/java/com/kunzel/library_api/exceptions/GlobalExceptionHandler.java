package com.kunzel.library_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(BookNotAvailableException.class)
  public ResponseEntity<String> handleBookNotAvailable(BookNotAvailableException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  };
}
