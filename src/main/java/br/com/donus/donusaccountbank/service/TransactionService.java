package br.com.donus.donusaccountbank.service;

import br.com.donus.donusaccountbank.domain.TransactionWrapper;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;

public interface TransactionService {

    BalanceProjection commitTransactionAndRetrieveNewBalance(final TransactionWrapper newTransactions, final String cpfOwner);
}
