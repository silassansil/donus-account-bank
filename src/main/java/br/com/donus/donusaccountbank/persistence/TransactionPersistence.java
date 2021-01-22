package br.com.donus.donusaccountbank.persistence;

import br.com.donus.donusaccountbank.domain.TransactionWrapper;

public interface TransactionPersistence {

    void saveAll(final TransactionWrapper transactions);
}
