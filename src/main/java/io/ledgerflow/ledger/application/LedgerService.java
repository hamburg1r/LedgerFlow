package io.ledgerflow.ledger.application;

import io.ledgerflow.ledger.api.BalanceResponse;
import io.ledgerflow.ledger.api.JournalRequest;
import io.ledgerflow.ledger.api.JournalResponse;
import io.ledgerflow.ledger.infra.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LedgerService {
    private final LedgerRepository ledgerRepository;

    @Transactional
    public void postJournal(JournalRequest journalRequest) {
        JournalMapper.journalEntryRequestToJournalEntry(journalRequest)
                .entries()
                .forEach(ledgerRepository::save);
    }

    @Transactional
    public BalanceResponse getBalanceByAccountId(UUID accountId) {
        return new BalanceResponse(
                accountId,
                ledgerRepository.getBalanceByAccountId(accountId));
    }

    public Optional<JournalResponse> getEntryByTransactionId(UUID id) {
        return JournalMapper.ledgerEntriesToJournalResponse(ledgerRepository.findByTransactionId(id));
    }
}
