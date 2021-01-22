package br.com.donus.donusaccountbank.service.impl;

import br.com.donus.donusaccountbank.MockFactory;
import br.com.donus.donusaccountbank.domain.Account;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.exception.InsufficientFundsException;
import br.com.donus.donusaccountbank.exception.UserAlreadyExistsException;
import br.com.donus.donusaccountbank.persistence.AccountPersistence;
import br.com.donus.donusaccountbank.service.CalculationBalanceService;
import br.com.donus.donusaccountbank.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountPersistence accountPersistence;

    @Mock
    private TransactionService transactionService;

    @Mock
    private CalculationBalanceService calculationBalanceService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void openAccountWithSuccess() {
        Mockito.when(this.accountPersistence.persistNewAccount(Mockito.any()))
                .thenReturn(MockFactory.buildAccount());

        final Account account = this.accountService.openAccount(MockFactory.buildCustomer());

        Assertions.assertNotNull(account);
        Assertions.assertEquals(LocalDate.now(), account.getCreatedAt());
        Assertions.assertEquals("Silas", account.getCustomer().getName());
        Mockito.verify(this.accountPersistence).persistNewAccount(Mockito.any());
    }

    @Test
    public void openAccountWithSameCpf() {
        Mockito.when(this.accountPersistence.persistNewAccount(Mockito.any()))
                .thenThrow(UserAlreadyExistsException.class);

        Assertions.assertThrows(
                UserAlreadyExistsException.class,
                () -> this.accountService.openAccount(MockFactory.buildCustomer()),
                "user already exist for this cpf");
        Mockito.verify(this.accountPersistence).persistNewAccount(Mockito.any());
    }

    @Test
    public void depositWhenIsValidaAmount() {
        Mockito.when(this.accountPersistence.findAccountEntityByCustomerCpf(Mockito.anyString()))
                .thenReturn(MockFactory.buildAccount());

        Mockito.when(this.transactionService.commitTransactionAndRetrieveNewBalance(Mockito.any(), Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection deposit = this.accountService.deposit(MockFactory.CPF_1, 1000.0);

        Assertions.assertNotNull(deposit);
        Assertions.assertEquals(1000.0, deposit.getAmount());
        Mockito.verify(this.accountPersistence).findAccountEntityByCustomerCpf(Mockito.any());
        Mockito.verify(this.transactionService).commitTransactionAndRetrieveNewBalance(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void depositWhenIsInvalidAmount() {
        Mockito.when(this.accountPersistence.findAccountEntityByCustomerCpf(Mockito.anyString()))
                .thenReturn(MockFactory.buildAccount());

        Mockito.when(this.transactionService.commitTransactionAndRetrieveNewBalance(Mockito.any(), Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection deposit = this.accountService.deposit(MockFactory.CPF_1, 1000.0);

        Assertions.assertThrows(
                InsufficientFundsException.class,
                () -> deposit.addAmount(-20000.0),
                "Invalid negative balance");
        Mockito.verify(this.accountPersistence).findAccountEntityByCustomerCpf(Mockito.any());
        Mockito.verify(this.transactionService).commitTransactionAndRetrieveNewBalance(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void transferWhenIsValidaAmount() {
        Mockito.when(this.accountPersistence.findAccountEntityByCustomerCpf(Mockito.anyString()))
                .thenReturn(MockFactory.buildAccount());

        Mockito.when(this.transactionService.commitTransactionAndRetrieveNewBalance(Mockito.any(), Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection deposit = this.accountService.transfer(MockFactory.CPF_1, MockFactory.CPF_2, 1000.0);

        Assertions.assertNotNull(deposit);
        Assertions.assertEquals(1000.0, deposit.getAmount());
        Mockito.verify(this.accountPersistence, Mockito.times(2)).findAccountEntityByCustomerCpf(Mockito.any());
        Mockito.verify(this.transactionService).commitTransactionAndRetrieveNewBalance(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void transferWhenIsInvalidAmount() {
        Mockito.when(this.accountPersistence.findAccountEntityByCustomerCpf(Mockito.anyString()))
                .thenReturn(MockFactory.buildAccount());

        Mockito.when(this.transactionService.commitTransactionAndRetrieveNewBalance(Mockito.any(), Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection deposit = this.accountService.transfer(MockFactory.CPF_1, MockFactory.CPF_2, 1000.0);

        Assertions.assertThrows(
                InsufficientFundsException.class,
                () -> deposit.addAmount(-20000.0),
                "Invalid negative balance");
        Mockito.verify(this.accountPersistence, Mockito.times(2)).findAccountEntityByCustomerCpf(Mockito.any());
        Mockito.verify(this.transactionService).commitTransactionAndRetrieveNewBalance(Mockito.any(), Mockito.anyString());
    }

    @Test
    public void getCurrentBalanceWithSuccess() {
        Mockito.when(this.calculationBalanceService.calculate(Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection balance = this.accountService.balance(MockFactory.CPF_1);

        Assertions.assertNotNull(balance);
        Assertions.assertEquals(1000.0, balance.getAmount());
        Mockito.verify(this.calculationBalanceService).calculate(Mockito.anyString());
    }
}