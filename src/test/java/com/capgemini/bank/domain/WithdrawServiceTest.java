package com.capgemini.bank.domain;

import com.capgemini.bank.domain.ports.AccountRepository;
import com.capgemini.bank.exception.AccountNotFoundException;
import com.capgemini.bank.exception.BalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private WithdrawService withdrawService;

    @BeforeEach
    void setUp() {
        withdrawService = new WithdrawService(accountRepository);
    }

    @Test
    void withdraw_ShouldSucceed_WhenBalanceIsSufficient() {
        // Arrange
        Long accountId = 1L;
        double initialBalance = 1000.0;
        double withdrawAmount = 500.0;
        Account account = new Account(accountId, initialBalance);
        WithdrawCommand command = new WithdrawCommand(accountId, withdrawAmount);

        when(accountRepository.findAccountById(accountId)).thenReturn(account);

        // Act
        Statement result = withdrawService.withdraw(command);

        // Assert
        assertNotNull(result);
        assertNotNull(result.id());
        assertNotNull(result.date());
        assertEquals(withdrawAmount, result.amount());

        verify(accountRepository).findAccountById(accountId);
        verify(accountRepository).saveAccount(any(Account.class));
    }

    @Test
    void withdraw_ShouldUpdateAccountWithCorrectBalance() {
        // Arrange
        Long accountId = 1L;
        double initialBalance = 1000.0;
        double withdrawAmount = 500.0;
        Account account = new Account(accountId, initialBalance);
        WithdrawCommand command = new WithdrawCommand(accountId, withdrawAmount);

        when(accountRepository.findAccountById(accountId)).thenReturn(account);

        // Act
        withdrawService.withdraw(command);

        // Assert
        verify(accountRepository).saveAccount(
                argThat(savedAccount ->
                        savedAccount.id().equals(accountId) &&
                                savedAccount.balance() == (initialBalance - withdrawAmount)
                )
        );
    }

    @Test
    void withdraw_ShouldThrowBalanceException_WhenBalanceIsInsufficient() {
        // Arrange
        Long accountId = 1L;
        double initialBalance = 100.0;
        double withdrawAmount = 500.0;
        Account account = new Account(accountId, initialBalance);
        WithdrawCommand command = new WithdrawCommand(accountId, withdrawAmount);

        when(accountRepository.findAccountById(accountId)).thenReturn(account);

        // Act & Assert
        BalanceException exception = assertThrows(
                BalanceException.class,
                () -> withdrawService.withdraw(command)
        );
        assertEquals("Balance in insufficient for this operation.", exception.getMessage());
        verify(accountRepository, never()).saveAccount(any());
    }

    @Test
    void withdraw_ShouldThrowAccountNotFoundException_WhenAccountDoesNotExist() {
        // Arrange
        Long accountId = 1L;
        WithdrawCommand command = new WithdrawCommand(accountId, 500.0);

        when(accountRepository.findAccountById(accountId))
                .thenThrow(new AccountNotFoundException("No account is matching this id: " + accountId));

        // Act & Assert
        assertThrows(
                AccountNotFoundException.class,
                () -> withdrawService.withdraw(command)
        );
        verify(accountRepository, never()).saveAccount(any());
    }
}