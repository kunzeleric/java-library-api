package com.kunzel.library_api.exceptions;

public class LoanAlreadyReturnedException extends RuntimeException {
  public LoanAlreadyReturnedException(Long loanId) {
    super("Empréstimo com ID " + loanId + " já foi devolvido.");
  }
}
