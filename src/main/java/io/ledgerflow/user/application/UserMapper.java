package io.ledgerflow.user.application;

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

    public User createRequestToUser(CreateUserRequest createRequest) {
        User user = new User();
        user.setName(createRequest.name());
        user.setEmail(createRequest.email());
        return user;
    }

    // Might not be needed
    public User createRequestToResponse(UUID id, CreateUserRequest createRequest) {
        User user = new User();
        user.setId(id);
        user.setName(createRequest.name());
        user.setEmail(createRequest.email());
        return user;
    }
}
