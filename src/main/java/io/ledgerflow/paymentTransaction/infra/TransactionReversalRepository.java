package io.ledgerflow.paymentTransaction.infra;

import io.ledgerflow.paymentTransaction.domain.ReversedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionReversalRepository extends JpaRepository<ReversedTransaction, UUID> {
    boolean existsByTransactionId(UUID transactionId);

    boolean existsByReversedTransactionId(UUID reversedTransactionId);
}
