package com.capgemini.bank.domain;

import com.capgemini.bank.domain.ports.AccountRepository;
import com.capgemini.bank.exception.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private DepositService depositService;

    @BeforeEach
    void setUp() {
        depositService = new DepositService(accountRepository);
    }

    @Test
    void deposit_ShouldSucceed_WhenAccountExists() {
        // Arrange
        Long accountId = 1L;
        double initialBalance = 1000.0;
        double depositAmount = 500.0;
        Account account = new Account(accountId, initialBalance);
        DepositCommand command = new DepositCommand(accountId, depositAmount);

        when(accountRepository.findAccountById(accountId)).thenReturn(account);

        // Act
        Statement result = depositService.deposit(command);

        // Assert
        assertNotNull(result);
        assertNotNull(result.id());
        assertNotNull(result.date());
        assertEquals(depositAmount, result.amount());

        verify(accountRepository).findAccountById(accountId);
        verify(accountRepository).saveAccount(any(Account.class));
    }

    @Test
    void deposit_ShouldUpdateAccountWithCorrectBalance() {
        // Arrange
        Long accountId = 1L;
        double initialBalance = 1000.0;
        double depositAmount = 500.0;
        Account account = new Account(accountId, initialBalance);
        DepositCommand command = new DepositCommand(accountId, depositAmount);

        when(accountRepository.findAccountById(accountId)).thenReturn(account);

        // Act
        depositService.deposit(command);

        // Assert
        verify(accountRepository).saveAccount(
                argThat(savedAccount ->
                        savedAccount.id().equals(accountId) &&
                                savedAccount.balance() == (initialBalance + depositAmount)
                )
        );
    }

    @Test
    void deposit_ShouldThrowAccountNotFoundException_WhenAccountDoesNotExist() {
        // Arrange
        Long accountId = 1L;
        DepositCommand command = new DepositCommand(accountId, 500.0);

        when(accountRepository.findAccountById(accountId))
                .thenThrow(new AccountNotFoundException("No account is matching this id: " + accountId));

        // Act & Assert
        assertThrows(
                AccountNotFoundException.class,
                () -> depositService.deposit(command)
        );
        verify(accountRepository, never()).saveAccount(any());
    }
}