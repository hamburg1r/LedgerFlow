package io.ledgerflow.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"accountId"}),
                @UniqueConstraint(columnNames = {"userId"})
        }
)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private UUID accountId;

    @Column(nullable = false)
    private long balanceMinor = 0L;

    @Column(nullable = false, updatable = false)
    private UUID userId;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Account(UUID accountId, UUID userId) {
        this.accountId = accountId;
        this.userId = userId;
    }

    public void applyBalanceProjection(Long amount) {
        this.balanceMinor = amount;
    }
}