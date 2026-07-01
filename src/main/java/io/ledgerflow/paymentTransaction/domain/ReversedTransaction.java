package io.ledgerflow.paymentTransaction.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table()
@NoArgsConstructor
public class ReversedTransaction {
    @Id
    @Getter
    @Column(nullable = false, unique = true)
    private UUID transactionId;

    @Getter
    @Column(nullable = false, unique = true)
    private UUID reversedTransactionId;

    @Version
    private int version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public ReversedTransaction(UUID transactionId, UUID reversedTransactionId) {
        this.transactionId = transactionId;
        this.reversedTransactionId = reversedTransactionId;
    }
}
