package io.ledgerflow.ledger.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record JournalRequest(
        @NotBlank UUID transactionId,
        @Size(min = 2) List<LedgerRequest> entries
) {
}
