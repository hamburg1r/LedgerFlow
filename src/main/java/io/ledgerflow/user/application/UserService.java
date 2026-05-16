package io.ledgerflow.user.application;

import io.ledgerflow.user.api.CreateUserRequest;
import io.ledgerflow.user.api.UserResponse;
import io.ledgerflow.user.domain.User;
import io.ledgerflow.user.error.UserNotFoundException;
import io.ledgerflow.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(CreateUserRequest userDto) {
        log.info("Creating user with email: {}", userDto.email());
        User user = userMapper.createRequestToUser(userDto, UUID.randomUUID());
        User saved = userRepository.save(user);
        log.info("User created with ID: {}", saved.getId());
        return userMapper.userToResponse(saved);
    }

    public UserResponse getUserById(UUID id) {
        log.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    log.info("User found with ID: {}", id);
                    return userMapper.userToResponse(user);
                })
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found");
                });
    }
}
