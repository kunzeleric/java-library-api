package com.kunzel.library_api.repositories;

import org.springframework.stereotype.Repository;

import com.kunzel.library_api.model.Loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
  @Modifying
  @Query("DELETE FROM Loan l WHERE l.book.id = :bookId")
  public void deleteByBookId(Long bookId);
}
