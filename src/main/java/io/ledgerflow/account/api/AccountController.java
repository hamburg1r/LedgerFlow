package io.ledgerflow.account.api;

import io.ledgerflow.account.application.AccountService;
import io.ledgerflow.account.error.AccountAlreadyExistsException;
import io.ledgerflow.account.error.AccountNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private static final Logger logger =LoggerFactory.getLogger(AccountController.class);

    @PostMapping()
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        try {
            logger.debug("Create request for: {}", createAccountRequest.userId());
            AccountResponse response = accountService.createAccount(createAccountRequest);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AccountAlreadyExistsException e) {
            logger.warn("Problem creating the account: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID accountId) {
        try {
            logger.debug("Get request for: {}", accountId);
            AccountResponse response = accountService.getAccountById(accountId);

            logger.debug("Account fetched for: {}", accountId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            logger.debug("Problem fetching the account: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}