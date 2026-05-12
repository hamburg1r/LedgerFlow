package io.ledgerflow.account.api;

import io.ledgerflow.account.domain.Account;

public class AccountMapper {
    public AccountResponse accountToResponse(Account account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getBalanceMinor(),
                account.getUserId()
        );
    }
}
