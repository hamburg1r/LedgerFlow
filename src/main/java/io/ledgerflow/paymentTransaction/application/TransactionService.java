package io.ledgerflow.paymentTransaction.application;

import io.ledgerflow.account.application.AccountService;
import io.ledgerflow.ledger.api.JournalResponse;
import io.ledgerflow.ledger.application.LedgerService;
import io.ledgerflow.ledger.domain.Direction;
import io.ledgerflow.paymentTransaction.api.EntryRequest;
import io.ledgerflow.paymentTransaction.api.ReversedTransactionResponse;
import io.ledgerflow.paymentTransaction.api.TransactionRequest;
import io.ledgerflow.paymentTransaction.api.TransactionResponse;
import io.ledgerflow.paymentTransaction.domain.Transaction;
import io.ledgerflow.paymentTransaction.domain.ReversedTransaction;
import io.ledgerflow.paymentTransaction.domain.TransactionState;
import io.ledgerflow.paymentTransaction.error.TransactionNotFoundException;
import io.ledgerflow.paymentTransaction.error.UnauthorizedTransaction;
import io.ledgerflow.paymentTransaction.error.UnauthorizedTransactionReversal;
import io.ledgerflow.paymentTransaction.infra.TransactionRepository;
import io.ledgerflow.paymentTransaction.infra.TransactionReversalRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
        private final TransactionRepository transactionRepository;
        private final TransactionReversalRepository transactionReversalRepository;

        private final LedgerService ledger;
        private final AccountService accountService;

        @Transactional
        public Optional<TransactionResponse> getTransaction(@NonNull UUID transactionId) {
                return transactionRepository.findById(transactionId)
                                .map(TransactionMapper::transactionToTransactionResponse);
        }

        @Transactional
        public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
                List<UUID> debitAccounts = transactionRequest.entries().stream()
                                .filter(entryRequest -> entryRequest.direction() == Direction.DEBIT)
                                .map(entryRequest -> entryRequest.accountId())
                                .toList();
                for (UUID debitAccount : debitAccounts) {
                        if (!accountService.canDebit(transactionRequest.initiator(), debitAccount))
                                throw new UnauthorizedTransaction(
                                                transactionRequest.initiator() +
                                                                "is not authorized to debit balance from " +
                                                                debitAccount);
                }
                ledger.postJournal(
                                TransactionMapper.transactionRequestToJournalRequest(transactionRequest));

                Transaction transaction = new Transaction(
                                transactionRequest.transactionId(),
                                TransactionState.POSTED,
                                transactionRequest.initiator());

                transactionRepository.save(transaction);

                return TransactionMapper.transactionToTransactionResponse(transaction);
        }

        @Transactional
        public ReversedTransactionResponse reverse(UUID transactionId, UUID initiatorId) {
                JournalResponse journalResponse = ledger.getEntryByTransactionId(transactionId)
                                .orElseThrow(
                                                () -> new TransactionNotFoundException("Transaction with id: "
                                                                + transactionId + " not found"));
                List<EntryRequest> reverseEntries = journalResponse.entries().stream().map(entry -> {
                        return new EntryRequest(
                                        entry.accountId(),
                                        entry.amountMinor(),
                                        entry.direction() == Direction.DEBIT ? Direction.CREDIT : Direction.DEBIT);
                }).toList();

                for (EntryRequest entry : reverseEntries) {
                        if (!accountService.canReverse(initiatorId, entry.accountId()))
                                throw new UnauthorizedTransactionReversal(
                                                initiatorId +
                                                                "is not authorized to reverse transaction: " +
                                                                transactionId);
                }

                Transaction newReversedTransaction = new Transaction(
                                UUID.randomUUID(),
                                TransactionState.POSTED,
                                initiatorId);

                ReversedTransaction reversedTransaction = new ReversedTransaction(
                                transactionId,
                                newReversedTransaction.getId());

                ledger.postJournal(TransactionMapper.transactionRequestToJournalRequest(
                                new TransactionRequest(
                                                reversedTransaction.getReversedTransactionId(),
                                                initiatorId,
                                                reverseEntries)));

                transactionRepository.save(newReversedTransaction);
                transactionReversalRepository.save(reversedTransaction);

                return TransactionMapper.reversedTransactionToReversedTransactionResponse(reversedTransaction);
        }
}