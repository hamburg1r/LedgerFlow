package io.ledgerflow.ledger.domain;

import io.ledgerflow.ledger.error.UnbalancedJournalException;

import java.util.List;

public class Journal {
    private final List<Ledger> entries;

    public Journal(List<Ledger> entries) {
        validate(entries);
        this.entries = List.copyOf(entries);
    }

    public List<Ledger> entries() {
        return List.copyOf(entries);
    }

    private void validate(List<Ledger> entries) {
        if (entries == null || entries.isEmpty()) {
            throw new IllegalArgumentException("Entries cannot be empty");
        }

        long debitTotal = entries.stream()
                .filter(Ledger::isDebit)
                .mapToLong(Ledger::amountMinor)
                .sum();

        long creditTotal = entries.stream()
                .filter(Ledger::isCredit)
                .mapToLong(Ledger::amountMinor)
                .sum();

        if (debitTotal != creditTotal) {
            throw new UnbalancedJournalException("Journal entry is unbalanced");
        }
    }
}
