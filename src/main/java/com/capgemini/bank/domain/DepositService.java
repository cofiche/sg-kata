package com.capgemini.bank.domain;

import com.capgemini.bank.domain.annotations.DomainService;
import com.capgemini.bank.domain.ports.AccountRepository;
import com.capgemini.bank.domain.ports.DepositUseCase;

import java.time.Instant;
import java.util.Random;
import java.util.logging.Logger;

@DomainService
public class DepositService implements DepositUseCase {

    private static final Logger logger = Logger.getLogger(DepositService.class.getName());

    private final AccountRepository accountRepository;

    public DepositService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Statement deposit(DepositCommand command) {
        var account = accountRepository.findAccountById(command.accountId());

        var updatedBalance = account.balance() + command.amount();

        logger.info("Updating balance for account with id: " + command.accountId());

        accountRepository.saveAccount(new Account(account.id(), updatedBalance));

        logger.info("Balance updated for account with id: " + command.accountId());

        return new Statement(new Random().nextLong(100), Instant.now(), command.amount());
    }
}
