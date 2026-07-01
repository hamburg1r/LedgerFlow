package io.ledgerflow.paymentTransaction.api;

import io.ledgerflow.ledger.domain.Direction;

import java.util.UUID;

public record EntryRequest(UUID accountId, long amountMinor, Direction direction) {}