package br.com.donus.donusaccountbank.service.impl;

import br.com.donus.donusaccountbank.MockFactory;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.exception.InsufficientFundsException;
import br.com.donus.donusaccountbank.persistence.repository.TransactionRepository;
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
class PreCommitTransactionImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private PreCommitTransactionImpl preCommitTransaction;

    @Test
    public void validatePreCommitWhenIsValidNewTransactions() {
        Mockito.when(this.transactionRepository.calculateCurrentBalance(Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection validate = this.preCommitTransaction.validate(MockFactory.buildTransactionWrapper(), MockFactory.CPF_1);

        Assertions.assertNotNull(validate);
        Assertions.assertEquals(2000, validate.getAmount());
        Mockito.verify(this.transactionRepository).calculateCurrentBalance(Mockito.anyString());
    }

    @Test
    public void validatePreCommitWhenIsInvalidNewTransactions() {
        Mockito.when(this.transactionRepository.calculateCurrentBalance(Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection validate = this.preCommitTransaction.validate(MockFactory.buildTransactionWrapper(), MockFactory.CPF_1);

        Assertions.assertThrows(
                InsufficientFundsException.class,
                () -> validate.addAmount(-4000.0),
                "Invalid negative balance");
        Mockito.verify(this.transactionRepository).calculateCurrentBalance(Mockito.anyString());
    }
}