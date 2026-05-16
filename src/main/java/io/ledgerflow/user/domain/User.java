package io.ledgerflow.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        }
)
public class User {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    protected User() {}

    public User(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
