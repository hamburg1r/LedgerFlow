package io.ledgerflow.account.api;

import io.ledgerflow.account.application.AccountService;
import io.ledgerflow.account.error.AccountAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        try {
            return new ResponseEntity<>(accountService.createAccount(createAccountRequest),
                    HttpStatus.CREATED);
        } catch (AccountAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID accountId) {
        return new ResponseEntity<>(accountService.getAccountById(accountId),
                HttpStatus.OK);
    }
}