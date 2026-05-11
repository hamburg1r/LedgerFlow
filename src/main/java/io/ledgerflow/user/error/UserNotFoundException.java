package io.ledgerflow.user.error;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNotFound) {
    }
}
