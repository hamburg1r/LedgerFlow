package io.ledgerflow.ledger.application;

import io.ledgerflow.ledger.api.JournalRequest;
import io.ledgerflow.ledger.api.JournalResponse;
import io.ledgerflow.ledger.domain.Journal;
import io.ledgerflow.ledger.domain.Ledger;

import java.util.List;
import java.util.Optional;

public class JournalMapper {
    public static Journal journalEntryRequestToJournalEntry(JournalRequest journalRequest) {
        return new Journal(
            journalRequest
                .entries()
                .stream()
                .map(
                    ledgerEntryRequest -> LedgerMapper.ledgerRequestToLedger(
                        ledgerEntryRequest,
                        journalRequest.transactionId()
                    )
                ).toList()
        );
    }

    public static Optional<JournalResponse> ledgerEntriesToJournalResponse(List<Ledger> entries) {
        if (entries == null || entries.isEmpty()) {
            return Optional.empty();
        }

        List<io.ledgerflow.ledger.api.LedgerResponse> responses = entries.stream()
                .map(LedgerMapper::ledgerToLedgerResponse)
                .toList();

        return Optional.of(new JournalResponse(responses));
    }
}
