package io.ledgerflow.paymentTransaction.api;

import io.ledgerflow.paymentTransaction.application.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable UUID transaciotnId) {
        return transactionService.getTransaction(transaciotnId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<TransactionResponse> createJournal(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }

    @PostMapping("/{transactionId}/reverse")
    public ResponseEntity<ReversedTransactionResponse> reverseTransaction(@PathVariable UUID transactionId,
            @RequestBody UUID initiatorId) {
        return ResponseEntity.ok(transactionService.reverse(transactionId, initiatorId));
        // return ResponseEntity.ok(new ReversedTransactionResponse(transactionId));
    }
}
