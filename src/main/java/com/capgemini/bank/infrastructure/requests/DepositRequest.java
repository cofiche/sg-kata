package com.capgemini.bank.infrastructure.requests;

public record DepositRequest(Long accountId, Double amount) {
}
