package com.kunzel.library_api.dtos.loan;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLoanRequest(@NotBlank(message = "não deve estar em branco") String borrowerName,
    @NotBlank(message = "não deve estar em branco") @Email String borrowerEmail,
    @NotNull(message = "deve ser válido") Long bookId) {

}
