package io.ledgerflow.ledger.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Table()
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private long amountMinor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column(nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private UUID transactionId;

    @Version
    private int version;

    @CreationTimestamp
    private Instant createdAt;

    private Ledger(
            long amountMinor,
            UUID accountId,
            Direction direction,
            UUID transactionId
    ) {
        this.amountMinor = amountMinor;
        this.accountId = accountId;
        this.direction = direction;
        this.transactionId = transactionId;
    }

    public static Ledger debit(
            long amountMinor,
            UUID accountId,
            UUID transactionId
    ) {
        return new Ledger(
                amountMinor,
                accountId,
                Direction.DEBIT,
                transactionId
        );
    }

    public  static Ledger credit(
            long amountMinor,
            UUID accountId,
            UUID transactionId
    ) {
        return new Ledger(
                amountMinor,
                accountId,
                Direction.CREDIT,
                transactionId
        );
    }

    public boolean isDebit() {
        return direction == Direction.DEBIT;
    }

    public boolean isCredit() {
        return direction == Direction.CREDIT;
    }

    public UUID accountId() {
        return accountId;
    }

    public long amountMinor() {
        return amountMinor;
    }

    public Direction direction() {
        return direction;
    }
}