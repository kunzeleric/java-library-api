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
  }

  @ExceptionHandler(AuthorWithBooksException.class)
  public ResponseEntity<String> handleAuthorWithBooksRemoval(AuthorWithBooksException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(DuplicatedIsbnException.class)
  public ResponseEntity<String> handleDuplicatedIsbn(DuplicatedIsbnException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(LoanAlreadyReturnedException.class)
  public ResponseEntity<String> handleLoanAlreadyReturned(LoanAlreadyReturnedException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
