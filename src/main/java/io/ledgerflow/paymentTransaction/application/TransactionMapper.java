package io.ledgerflow.paymentTransaction.application;

import java.util.List;
import java.util.UUID;

import io.ledgerflow.ledger.api.JournalRequest;
import io.ledgerflow.ledger.api.LedgerRequest;
import io.ledgerflow.paymentTransaction.api.EntryRequest;
import io.ledgerflow.paymentTransaction.api.ReversedTransactionResponse;
import io.ledgerflow.paymentTransaction.api.TransactionRequest;
import io.ledgerflow.paymentTransaction.api.TransactionResponse;
import io.ledgerflow.paymentTransaction.domain.ReversedTransaction;
import io.ledgerflow.paymentTransaction.domain.Transaction;

public class TransactionMapper {

    public static TransactionResponse transactionToTransactionResponse(Transaction transaction) {
        return new TransactionResponse(transaction.getId(), transaction.getState(), transaction.getInitiatedBy());
    }

    public static LedgerRequest entryToLedgerRequest(EntryRequest entryRequest) {
        return new LedgerRequest(
                entryRequest.accountId(),
                entryRequest.amountMinor(),
                entryRequest.direction());
    }

    public static JournalRequest transactionRequestToJournalRequest(TransactionRequest transactionRequest) {
        return new JournalRequest(
                transactionRequest.transactionId(),
                transactionRequest
                        .entries()
                        .stream()
                        .map(TransactionMapper::entryToLedgerRequest).toList());
    }

    public static ReversedTransactionResponse reversedTransactionToReversedTransactionResponse(
            ReversedTransaction reversedTransaction) {
        return new ReversedTransactionResponse(
                reversedTransaction.getTransactionId(),
                reversedTransaction.getReversedTransactionId());
    }
}