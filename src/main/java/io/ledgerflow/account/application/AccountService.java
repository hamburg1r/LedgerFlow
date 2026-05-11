package io.ledgerflow.account.application;

import io.ledgerflow.account.api.AccountMapper;
import io.ledgerflow.account.api.AccountResponse;
import io.ledgerflow.account.api.CreateAccountRequest;
import io.ledgerflow.account.domain.Account;
import io.ledgerflow.account.error.AccountAlreadyExistsException;
import io.ledgerflow.account.error.AccountNotFoundException;
import io.ledgerflow.account.infra.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) throws AccountAlreadyExistsException {
        try {
            Account account = accountMapper.createRequestToAccount(createAccountRequest);
            UUID id = UUID.randomUUID();
            account.setAccountId(id);
            accountRepository.save(account);
            return accountMapper.accountToResponse(account);
        } catch (DataIntegrityViolationException e) {
            throw new AccountAlreadyExistsException("Wallet already exists for user:" + createAccountRequest.userId());
        }
    }

    public AccountResponse getAccountById(UUID id) {
        Account account = accountRepository.findByAccountId(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        // TODO: run calculations
        return accountMapper.accountToResponse(account);
    }
}
