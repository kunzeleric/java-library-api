package com.kunzel.library_api.dtos.loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.kunzel.library_api.model.Book;
import com.kunzel.library_api.model.Loan;

public record LoanResponse(Long loanId, String borrowerName, LocalDate dueDate, LocalDate returnDate, Book book,
    Long daysLate,
    Boolean late) {

  public LoanResponse(Loan loan) {
    this(loan.getId(), loan.getBorrowerName(), loan.getDueDate(), loan.getReturnDate(), loan.getBook(),
        calculateDaysLate(loan),
        isLoanLate(loan));
  }

  private static Boolean isLoanLate(Loan loan) {
    LocalDate dueDate = loan.getDueDate();
    LocalDate currentDate = LocalDate.now();
    LocalDate returnDate = loan.getReturnDate();

    if (returnDate != null) {
      if (returnDate.isAfter(dueDate))
        return true;
      else
        return false;
    }

    return currentDate.isAfter(dueDate);
  }

  // Days Late can be calculated either from RETURN DATE or from CURRENT DATE
  // e.g. if the book is not returned, you cannot calculate range date from due
  // date against a NULL date
  // If the book is returned, then of course the daysLate will always be
  // returnDate - dueDate difference in days
  private static Long calculateDaysLate(Loan loan) {
    if (isLoanLate(loan)) {
      LocalDate returnDate = loan.getReturnDate();
      LocalDate dueDate = loan.getDueDate();

      // if Return Date is NOT NULL, then we can calculate days late difference
      // only if the return date is AFTER the due date..
      if (returnDate != null) {
        return ChronoUnit.DAYS.between(dueDate, returnDate);
      }
    }

    // Return 0 days because loan is NOT late
    return 0L;
  }
}
