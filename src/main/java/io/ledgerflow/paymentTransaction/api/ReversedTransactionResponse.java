package io.ledgerflow.paymentTransaction.api;

import java.util.UUID;

public record ReversedTransactionResponse(UUID transactionId, UUID reversedTransactionId) {}
