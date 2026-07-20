package com.kunzel.library_api.dtos.loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.kunzel.library_api.dtos.book.BookResponse;
import com.kunzel.library_api.model.Loan;

public record LoanResponse(Long loanId, String borrowerName, LocalDate dueDate, LocalDate returnDate, BookResponse book,
    Long daysLate,
    Boolean late) {

  public LoanResponse(Loan loan) {
    this(loan.getId(), loan.getBorrowerName(), loan.getDueDate(), loan.getReturnDate(),
        new BookResponse(loan.getBook()),
        calculateDaysLate(loan),
        isLoanLate(loan));
  }

  private static Boolean isLoanLate(Loan loan) {
    LocalDate referenceDate = loan.getReturnDate() != null ? loan.getReturnDate() : LocalDate.now();
    return referenceDate.isAfter(loan.getDueDate());
  }

  private static Long calculateDaysLate(Loan loan) {
    if (!isLoanLate(loan)) {
      return 0L;
    }

    LocalDate referenceDate = loan.getReturnDate() != null ? loan.getReturnDate() : LocalDate.now();

    return ChronoUnit.DAYS.between(loan.getDueDate(), referenceDate);
  }

}
