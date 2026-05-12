package io.ledgerflow.account.application;

import io.ledgerflow.account.api.AccountMapper;
import io.ledgerflow.account.api.AccountResponse;
import io.ledgerflow.account.api.CreateAccountRequest;
import io.ledgerflow.account.domain.Account;
import io.ledgerflow.account.error.AccountAlreadyExistsException;
import io.ledgerflow.account.error.AccountNotFoundException;
import io.ledgerflow.account.infra.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) throws AccountAlreadyExistsException {
        try {
            Account account = new Account(createAccountRequest.userId());
            accountRepository.save(account);

            logger.info("Account created for: {}", createAccountRequest.userId());
            return accountMapper.accountToResponse(account);
        } catch (DataIntegrityViolationException e) { // Unique constraint check: https://stackoverflow.com/questions/39557914/how-to-get-uniqueviolationexception-instead-of-org-postgresql-util-psqlexception
            logger.debug("Duplicate account creation request for: {}", createAccountRequest.userId());
            return accountMapper.accountToResponse(
                    accountRepository.findByUserId(createAccountRequest.userId())
                            .orElseThrow(() -> new AccountNotFoundException("Cannot fetch account by user id:"+createAccountRequest.userId()))
            );
        }
    }

    public AccountResponse getAccountById(UUID id) {
        Account account = accountRepository.findByAccountId(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id: " + id + " not found"));
        return accountMapper.accountToResponse(account);
    }
}
