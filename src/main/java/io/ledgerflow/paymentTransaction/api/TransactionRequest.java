package io.ledgerflow.paymentTransaction.api;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record TransactionRequest(@NotNull UUID transactionId, UUID initiator, List<EntryRequest> entries) {}
