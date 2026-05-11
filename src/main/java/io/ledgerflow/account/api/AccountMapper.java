package io.ledgerflow.account.api;

import io.ledgerflow.account.domain.Account;

public class AccountMapper {
    public Account createRequestToAccount(CreateAccountRequest createAccountRequest) {
        Account account = new Account();
        account.setBalanceMinor(0L);
        account.setUserId(createAccountRequest.userId());
        return account;
    }

    public AccountResponse accountToResponse(Account account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getBalanceMinor(),
                account.getUserId()
        );
    }
}
