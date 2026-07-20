package com.kunzel.library_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public Loan createLoan(Long bookId, String borrowerName, String borrowerEmail) {
    Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(bookId));
    book.markAsLoaned();
    return loanRepository.save(new Loan(borrowerName, borrowerEmail, book));
  }

  public Page<Loan> getAllLoans(Pageable pageable) {
    return loanRepository.findAll(pageable);
  }

  @Transactional
  public Loan returnLoan(Long loanId) {
    Loan foundLoan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException(loanId));
    foundLoan.markAsReturned();
    return foundLoan;
  }
}
