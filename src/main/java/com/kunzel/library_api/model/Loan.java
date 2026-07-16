package com.kunzel.library_api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loans")
public class Loan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String borrowerName;
  private String borrowerEmail;
  private LocalDate loanDate;
  private LocalDate dueDate;
  private LocalDate returnDate;

  @ManyToOne
  @JoinColumn(name = "book", nullable = false)
  Book book;

  public Loan(String borrowerName, String borrowerEmail, LocalDate dueDate, LocalDate returnDate,
      Book book) {
    this.borrowerName = borrowerName;
    this.borrowerEmail = borrowerEmail;
    this.loanDate = LocalDate.now();
    this.dueDate = dueDate;
    this.returnDate = returnDate;
    this.book = book;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBorrowerName() {
    return borrowerName;
  }

  public void setBorrowerName(String borrowerName) {
    this.borrowerName = borrowerName;
  }

  public String getBorrowerEmail() {
    return borrowerEmail;
  }

  public void setBorrowerEmail(String borrowerEmail) {
    this.borrowerEmail = borrowerEmail;
  }

  public LocalDate getLoanDate() {
    return loanDate;
  }

  public void setLoanDate(LocalDate loanDate) {
    this.loanDate = loanDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

}
