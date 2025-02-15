package com.capgemini.bank.infrastructure.requests;

public record WithdrawRequest(Long accountId, Double amount) {
}
