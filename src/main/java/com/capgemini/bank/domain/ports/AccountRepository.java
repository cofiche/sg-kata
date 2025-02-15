package com.capgemini.bank.domain.ports;

import com.capgemini.bank.domain.Account;

public interface AccountRepository {

    Account findAccountById(Long accountId);
    void saveAccount(Account account);

}
