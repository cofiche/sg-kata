package com.capgemini.bank.domain.ports;

import com.capgemini.bank.domain.DepositCommand;
import com.capgemini.bank.domain.Statement;

public interface DepositUseCase {

    Statement deposit(DepositCommand command);


}
