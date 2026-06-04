package io.ledgerflow.ledger.api;

import java.util.List;

public record JournalResponse(
        List<LedgerResponse> entries
) {
}
