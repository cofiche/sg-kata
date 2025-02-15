package com.capgemini.bank.domain;

import com.capgemini.bank.infrastructure.requests.DepositRequest;

public record DepositCommand(Long accountId, Double amount) {

    public static DepositCommand toCommand(DepositRequest request) {
        return new DepositCommand(request.accountId(), request.amount());
    }
}
