package com.capgemini.bank.domain;

import com.capgemini.bank.infrastructure.requests.WithdrawRequest;

public record WithdrawCommand(Long accountId, Double amount) {

    public static WithdrawCommand toCommand(WithdrawRequest request) {
        return new WithdrawCommand(request.accountId(), request.amount());
    }
}
