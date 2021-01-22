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
class CalculationBalanceServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CalculationBalanceServiceImpl calculationBalanceService;

    @Test
    public void calculateBalanceProjectionByCpfOwner() {
        Mockito.when(this.transactionRepository.calculateCurrentBalance(Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection calculate = this.calculationBalanceService.calculate(MockFactory.CPF_1);

        Assertions.assertNotNull(calculate);
        Assertions.assertEquals(1000.0, calculate.getAmount());
        Mockito.verify(this.transactionRepository).calculateCurrentBalance(Mockito.anyString());
    }

    @Test
    public void calculateBalanceProjectionByCpfOwnerWhenHasNegativeBalancer() {
        Mockito.when(this.transactionRepository.calculateCurrentBalance(Mockito.anyString()))
                .thenReturn(MockFactory.buildBalanceProjection());

        final BalanceProjection calculate = this.calculationBalanceService.calculate(MockFactory.CPF_1);

        Assertions.assertThrows(
                InsufficientFundsException.class,
                () -> calculate.addAmount(-2000.0),
                "Invalid negative balance");
        Mockito.verify(this.transactionRepository).calculateCurrentBalance(Mockito.anyString());
    }
}