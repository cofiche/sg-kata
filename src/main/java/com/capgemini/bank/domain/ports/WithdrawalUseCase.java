package com.capgemini.bank.domain.ports;

import com.capgemini.bank.domain.Statement;
import com.capgemini.bank.domain.WithdrawCommand;

public interface WithdrawalUseCase {
    Statement withdraw(WithdrawCommand command);
}
