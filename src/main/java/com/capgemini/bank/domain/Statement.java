package com.capgemini.bank.domain;

import java.time.Instant;

public record Statement(Long id, Instant date, Double amount) {
}
