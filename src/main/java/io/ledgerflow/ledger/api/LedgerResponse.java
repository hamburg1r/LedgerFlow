package io.ledgerflow.ledger.api;

import io.ledgerflow.ledger.domain.Direction;

import java.util.UUID;

public record LedgerResponse(
        UUID accountId,
        long amountMinor,
        Direction direction
) {
}
