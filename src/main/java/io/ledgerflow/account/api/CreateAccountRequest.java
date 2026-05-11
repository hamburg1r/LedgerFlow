package io.ledgerflow.account.api;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateAccountRequest(@NotBlank UUID userId) {}
