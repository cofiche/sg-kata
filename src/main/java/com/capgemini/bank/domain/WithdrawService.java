package com.capgemini.bank.domain;

import com.capgemini.bank.domain.annotations.DomainService;
import com.capgemini.bank.domain.ports.AccountRepository;
import com.capgemini.bank.domain.ports.WithdrawalUseCase;
import com.capgemini.bank.exception.BalanceException;

import java.time.Instant;
import java.util.Random;
import java.util.logging.Logger;

@DomainService
public class WithdrawService implements WithdrawalUseCase {

    private static final Logger logger = Logger.getLogger(WithdrawService.class.getName());

    private final AccountRepository accountRepository;

    public WithdrawService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Statement withdraw(WithdrawCommand command) {
        logger.info("Checking balance for account with id: " + command.accountId());
        var account = accountRepository.findAccountById(command.accountId());

        if (account.balance() < command.amount()) {
            logger.warning("Account balance is not sufficient");
            throw new BalanceException("Balance in insufficient for this operation.");
        }

        var updatedBalance = account.balance() - command.amount();

        logger.info("Updating balance for account with id: " + command.accountId());
        accountRepository.saveAccount(new Account(account.id(), updatedBalance));
        logger.info("Balance updated for account with id: " + command.accountId());

        return new Statement(new Random().nextLong(100), Instant.now(), command.amount());
    }
}
