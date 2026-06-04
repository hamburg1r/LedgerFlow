package io.ledgerflow.ledger.infra;

import io.ledgerflow.ledger.domain.Ledger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LedgerRepository extends Repository<Ledger, Long> {
    Ledger save(Ledger ledger);

    List<Ledger> findByTransactionId(UUID transactionId);

    @Query("""
        SELECT COALESCE(
            SUM(
                CASE
                    WHEN l.direction = io.ledgerflow.ledger.domain.Direction.CREDIT
                    THEN l.amount
                    ELSE -l.amount
                END
            ),
            0
        )
        FROM Ledger l
        WHERE l.accountId = :accountId
    """)
    long getBalanceByAccountId(@Param("accountId") UUID accountId);
}
