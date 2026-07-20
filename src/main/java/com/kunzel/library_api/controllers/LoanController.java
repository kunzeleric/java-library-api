package com.kunzel.library_api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunzel.library_api.dtos.loan.CreateLoanRequest;
import com.kunzel.library_api.dtos.loan.LoanResponse;
import com.kunzel.library_api.model.Loan;
import com.kunzel.library_api.services.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loans")
public class LoanController {
  private final LoanService loanService;

  public LoanController(LoanService loanService) {
    this.loanService = loanService;
  }

  @GetMapping
  public ResponseEntity<Page<LoanResponse>> getAllLoans(Pageable pageable,
      @RequestParam(required = false) Boolean active) {
    Page<LoanResponse> loans = loanService.getAllLoans(pageable, active).map(LoanResponse::new);

    return ResponseEntity.ok().body(loans);
  }

  @PostMapping
  public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request) {
    Loan created = loanService.createLoan(request.bookId(), request.borrowerName(), request.borrowerEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(new LoanResponse(created));
  }

  @PatchMapping("/{id}/return")
  public ResponseEntity<LoanResponse> returnLoan(@PathVariable("id") long id) {
    Loan loan = loanService.returnLoan(id);
    return ResponseEntity.ok().body(new LoanResponse(loan));
  }
}
