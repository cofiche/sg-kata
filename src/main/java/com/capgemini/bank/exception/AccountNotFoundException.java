package com.capgemini.bank.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String s) {
        super(s);
    }
}
