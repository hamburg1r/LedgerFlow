package io.ledgerflow.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Setter
    @Column(nullable = false)
    private Long balanceMinor = 0L;

    @Column(nullable = false, updatable = false)
    private UUID userId;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Account(UUID userId) {
        this.accountId = UUID.randomUUID();
        this.userId = userId;
    }
}
