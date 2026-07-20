package com.kunzel.library_api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kunzel.library_api.exceptions.BookNotAvailableException;
import com.kunzel.library_api.exceptions.NotFoundException;
import com.kunzel.library_api.model.Book;
import com.kunzel.library_api.model.Loan;
import com.kunzel.library_api.repositories.BookRepository;
import com.kunzel.library_api.repositories.LoanRepository;

@Service
public class LoanService {
  private final LoanRepository loanRepository;
  private final BookRepository bookRepository;

  public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
    this.loanRepository = loanRepository;
    this.bookRepository = bookRepository;
  }

  public Loan createLoan(Long bookId, String borrowerName, String borrowerEmail) {
    Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(bookId));

    if (!book.isAvailable()) {
      throw new BookNotAvailableException(bookId);
    }

    Loan created = new Loan(borrowerName, borrowerEmail, book);

    book.setAvailable(false);
    bookRepository.save(book);

    return loanRepository.save(created);
  }

  public List<Loan> getAllLoans() {
    return loanRepository.findAll();
  }

  public Loan returnLoan(Long loanId) {
    Loan foundLoan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException(loanId));
    Book loanBook = bookRepository.findById(foundLoan.getBook().getId())
        .orElseThrow(() -> new NotFoundException(foundLoan.getBook().getId()));

    LocalDate returnDate = LocalDate.now();

    foundLoan.setReturnDate(returnDate);

    loanBook.setAvailable(true);
    bookRepository.save(loanBook);

    return loanRepository.save(foundLoan);
  }
}
