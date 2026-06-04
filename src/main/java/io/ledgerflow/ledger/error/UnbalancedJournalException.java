package io.ledgerflow.ledger.error;

public class UnbalancedJournalException extends RuntimeException {
    public UnbalancedJournalException(String journalEntryIsUnbalanced) {}
}
