package io.ledgerflow.ledger.api;

import java.util.UUID;

public record BalanceResponse(
        UUID accountId,
        long balanceMinor
) {}
