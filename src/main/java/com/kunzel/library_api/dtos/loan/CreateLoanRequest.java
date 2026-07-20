package com.kunzel.library_api.dtos.loan;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLoanRequest(@NotBlank String borrowerName, @Nullable String borrowerEmail, @NotNull Long bookId) {

}
