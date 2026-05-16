package io.ledgerflow.user.application;

import io.ledgerflow.user.api.CreateUserRequest;
import io.ledgerflow.user.api.UserResponse;
import io.ledgerflow.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    public UserResponse userToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public User createRequestToUser(CreateUserRequest createRequest, UUID id) {
        return new User((id!= null) ? id : UUID.randomUUID(),
                createRequest.name(),
                createRequest.email());
    }
}
