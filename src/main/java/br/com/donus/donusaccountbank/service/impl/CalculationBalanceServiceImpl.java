package br.com.donus.donusaccountbank.service.impl;

import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.persistence.repository.TransactionRepository;
import br.com.donus.donusaccountbank.service.CalculationBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationBalanceServiceImpl implements CalculationBalanceService {

    private final TransactionRepository transactionRepository;

    @Override
    public BalanceProjection calculate(final String cpfOwner) {
        return this.transactionRepository.calculateCurrentBalance(cpfOwner);
    }
}
