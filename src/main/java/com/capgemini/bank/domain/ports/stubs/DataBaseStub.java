package com.capgemini.bank.domain.ports.stubs;

import com.capgemini.bank.domain.Account;
import com.capgemini.bank.domain.annotations.Stub;
import com.capgemini.bank.domain.ports.AccountRepository;
import com.capgemini.bank.exception.AccountNotFoundException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Stub
public class DataBaseStub implements AccountRepository {

    private static final Set<Account> accounts = new HashSet<>();


    static {
        accounts.add(new Account(1L, 1000.00));
        accounts.add(new Account(2L, 2500.00));
        accounts.add(new Account(3L, 750.50));
        accounts.add(new Account(4L, 3200.75));
        accounts.add(new Account(5L,1500.25));
    }


    @Override
    public Account findAccountById(Long accountId) {
        return accounts.stream()
                .filter(account -> Objects.equals(account.id(), accountId))
                .findFirst().orElseThrow(() -> new AccountNotFoundException("No account is matching this id: "+ accountId));
    }

    @Override
    public void saveAccount(Account account) {
        accounts.add(account);
    }
}
