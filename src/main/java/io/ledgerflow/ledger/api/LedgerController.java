package io.ledgerflow.ledger.api;

import io.ledgerflow.ledger.application.LedgerService;
import io.ledgerflow.ledger.error.UnbalancedJournalException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ledger")
public class LedgerController {
    private final LedgerService ledgerService;

    @PostMapping
    public ResponseEntity<Void> addEntries(@RequestBody JournalRequest entry) {
        try {
            ledgerService.postJournal(entry);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UnbalancedJournalException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID accountId) {
        return ResponseEntity.ok(ledgerService.getBalanceByAccountId(accountId));
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<JournalResponse> getEntry(@PathVariable UUID transactionId) {
        return ledgerService.getEntryByTransactionId(transactionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
