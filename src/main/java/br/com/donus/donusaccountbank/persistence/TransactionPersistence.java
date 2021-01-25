package br.com.donus.donusaccountbank.persistence;

import br.com.donus.donusaccountbank.domain.TransactionWrapper;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;

public interface TransactionPersistence {

    void saveAll(final TransactionWrapper transactions);

    BalanceProjection calculate(final String cpfOwner);
}
