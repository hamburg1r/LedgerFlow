package io.ledgerflow.ledger.application;

import io.ledgerflow.ledger.api.LedgerRequest;
import io.ledgerflow.ledger.api.LedgerResponse;
import io.ledgerflow.ledger.domain.Direction;
import io.ledgerflow.ledger.domain.Ledger;

import java.util.UUID;

public class LedgerMapper {
    public static Ledger ledgerRequestToLedger(
            LedgerRequest ledgerEntryRequest,
            UUID transactionId
    ) {
        if (ledgerEntryRequest.direction() == Direction.DEBIT) {
            return Ledger.debit(
                    ledgerEntryRequest.amountMinor(),
                    ledgerEntryRequest.accountId(),
                    transactionId
            );
        } else {
            return Ledger.credit(
                    ledgerEntryRequest.amountMinor(),
                    ledgerEntryRequest.accountId(),
                    transactionId
            );
        }
    }

    public static LedgerResponse ledgerToLedgerResponse(
            Ledger ledger
    ) {
        return new LedgerResponse(
                ledger.accountId(),
                ledger.amountMinor(),
                ledger.direction()
        );
    }

}
