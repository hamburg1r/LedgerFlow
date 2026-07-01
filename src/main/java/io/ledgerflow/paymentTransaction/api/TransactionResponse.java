package io.ledgerflow.paymentTransaction.api;

import io.ledgerflow.paymentTransaction.domain.TransactionState;

import java.util.UUID;

public record TransactionResponse(
        UUID id,
        TransactionState state,
        UUID initiatedBy
) {}
