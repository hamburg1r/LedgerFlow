package io.ledgerflow.paymentTransaction.infra;

import io.ledgerflow.paymentTransaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {}
