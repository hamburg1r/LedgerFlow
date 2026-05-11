package io.ledgerflow.user.application;

import io.ledgerflow.user.api.CreateUserRequest;
import io.ledgerflow.user.api.UserMapper;
import io.ledgerflow.user.api.UserResponse;
import io.ledgerflow.user.domain.User;
import io.ledgerflow.user.error.UserNotFoundException;
import io.ledgerflow.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(CreateUserRequest userDto) {
        User user = userMapper.createRequestToUser(userDto);
        UUID id = UUID.randomUUID();
        user.setId(id);
        User saved = userRepository.save(user);
        return userMapper.userToResponse(saved);
    }

    public UserResponse getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::userToResponse)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
