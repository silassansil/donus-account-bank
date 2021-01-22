package br.com.donus.donusaccountbank.service.impl;

import br.com.donus.donusaccountbank.domain.TransactionWrapper;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.persistence.TransactionPersistence;
import br.com.donus.donusaccountbank.service.PreCommitTransaction;
import br.com.donus.donusaccountbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionPersistence transactionPersistence;
    private final PreCommitTransaction preCommitTransaction;

    @Override
    public BalanceProjection commitTransactionAndRetrieveNewBalance(final TransactionWrapper newTransactions,
                                                                    final String cpfOwner) {
        final BalanceProjection balanceProjection = this.preCommitTransaction.validate(newTransactions, cpfOwner);
        this.transactionPersistence.saveAll(newTransactions);

        return balanceProjection;
    }
}
