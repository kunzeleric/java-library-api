package com.kunzel.library_api.exceptions;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kunzel.library_api.dtos.FieldErrorResponse;

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

  @ExceptionHandler(DuplicatedAuthorException.class)
  public ResponseEntity<String> handleDuplicatedAuthor(DuplicatedAuthorException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<FieldErrorResponse>> handleFieldsValidation(MethodArgumentNotValidException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ex.getBindingResult().getFieldErrors().stream().map(FieldErrorResponse::new).toList());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrity() {
    return ResponseEntity.status(HttpStatus.CONFLICT).body("Operação viola uma restrição de integridade dos dados.");
  }
}
