package br.com.donus.donusaccountbank.service.impl;

import br.com.donus.donusaccountbank.domain.TransactionWrapper;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.persistence.repository.TransactionRepository;
import br.com.donus.donusaccountbank.service.PreCommitTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreCommitTransactionImpl implements PreCommitTransaction {

    private final TransactionRepository transactionRepository;

    @Override
    public BalanceProjection validate(final TransactionWrapper newTransactions, final String cpfOwner) {
        final double sumNewTransactionValue = newTransactions.calculateNewTransactionValues(cpfOwner);
        return this.transactionRepository.calculateCurrentBalance(cpfOwner)
                .addAmount(sumNewTransactionValue);
    }
}
