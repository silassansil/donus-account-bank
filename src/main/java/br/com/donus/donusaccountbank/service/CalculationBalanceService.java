package br.com.donus.donusaccountbank.service;

import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;

public interface CalculationBalanceService {

    BalanceProjection calculate(final String cpfOwner);
}
