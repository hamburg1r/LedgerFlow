package io.ledgerflow.account.api;

import java.util.UUID;

public record AccountResponse(UUID walletId, Long balanceMinor, UUID userId) {}
