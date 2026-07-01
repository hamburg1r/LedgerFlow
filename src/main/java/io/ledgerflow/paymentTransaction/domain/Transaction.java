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
public class Transaction {
    @Id
    @Column(nullable = false)
    @Getter
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    private TransactionState state;

    @Column(nullable = false)
    @Getter
    private UUID initiatedBy;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Transaction(
        UUID id,
        TransactionState state,
        UUID initiatedBy
    ) {
        this.id = id;
        this.state = state;
        this.initiatedBy = initiatedBy;
    }
}
