package com.kunzel.library_api.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kunzel.library_api.dtos.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  public ProblemDetail handleNotFound(NotFoundException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    problem.setTitle("Recurso não encontrado");
    return problem;
  }

  @ExceptionHandler(BookNotAvailableException.class)
  public ProblemDetail handleBookNotAvailable(BookNotAvailableException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    problem.setTitle("Livro indisponível");
    return problem;
  }

  @ExceptionHandler(AuthorWithBooksException.class)
  public ProblemDetail handleAuthorWithBooksRemoval(AuthorWithBooksException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    problem.setTitle("Autor com Livros Registrados");
    return problem;
  }

  @ExceptionHandler(DuplicatedIsbnException.class)
  public ProblemDetail handleDuplicatedIsbn(DuplicatedIsbnException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    problem.setTitle("ISBN Duplicado");
    return problem;
  }

  @ExceptionHandler(LoanAlreadyReturnedException.class)
  public ProblemDetail handleLoanAlreadyReturned(LoanAlreadyReturnedException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    problem.setTitle("Livro Já Retornado");
    return problem;
  }

  @ExceptionHandler(DuplicatedAuthorException.class)
  public ProblemDetail handleDuplicatedAuthor(DuplicatedAuthorException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    problem.setTitle("Autor Duplicado");
    return problem;
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos são inválidos");
    problem.setTitle("Erro de Validação");
    problem.setProperty("fieldErrors",
        ex.getBindingResult().getFieldErrors().stream().map(FieldErrorResponse::new).toList());

    return ResponseEntity.badRequest().body(problem);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex) {
    log.warn("Violação de integridade", ex);
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
        "Operação viola uma restrição de integridade dos dados.");
    problem.setTitle("Restrição de Dados");
    return problem;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleUnexpected(Exception ex) {
    log.error("Erro não tratado: ", ex);
    return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno.");
  }
}
