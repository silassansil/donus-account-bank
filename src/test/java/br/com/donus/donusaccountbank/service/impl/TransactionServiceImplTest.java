package br.com.donus.donusaccountbank.service.impl;

import br.com.donus.donusaccountbank.MockFactory;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.persistence.TransactionPersistence;
import br.com.donus.donusaccountbank.service.PreCommitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionPersistence transactionPersistence;

    @Mock
    private PreCommitTransaction preCommitTransaction;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void commitTransactionAndRetrieveNewBalanceWhenIsValid() {
        Mockito.when(this.preCommitTransaction.validate(Mockito.any(), Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection balanceProjection = this.transactionService.commitTransactionAndRetrieveNewBalance(
                MockFactory.buildTransactionWrapper(), MockFactory.CPF_2);

        Assertions.assertNotNull(balanceProjection);
        Assertions.assertEquals(1000, balanceProjection.getAmount());
        Mockito.verify(this.preCommitTransaction).validate(Mockito.any(), Mockito.anyString());
        Mockito.verify(this.transactionPersistence).saveAll(Mockito.any());
    }

    @Test
    public void retrieveBalanceFromTransactionsFromDatabase() {
        Mockito.when(this.transactionPersistence.calculate(Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection calculate = this.transactionService.calculate(MockFactory.CPF_2);

        Assertions.assertNotNull(calculate);
        Assertions.assertEquals(1000.0, calculate.getAmount());
        Mockito.verify(this.transactionPersistence).calculate(Mockito.anyString());
    }

    @Test
    public void retrieveBalanceFromTransactionsFromCache() {
        Mockito.when(this.preCommitTransaction.validate(Mockito.any(), Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        this.transactionService.commitTransactionAndRetrieveNewBalance(MockFactory.buildTransactionWrapper(), MockFactory.CPF_2);
        final BalanceProjection calculate = this.transactionService.calculate(MockFactory.CPF_2);

        Assertions.assertNotNull(calculate);
        Assertions.assertEquals(1000.0, calculate.getAmount());
        Mockito.verify(this.preCommitTransaction).validate(Mockito.any(), Mockito.anyString());
    }
}