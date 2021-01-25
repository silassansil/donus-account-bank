package br.com.donus.donusaccountbank.service.impl;

import br.com.donus.donusaccountbank.domain.TransactionWrapper;
import br.com.donus.donusaccountbank.domain.data.LRUCache;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.persistence.TransactionPersistence;
import br.com.donus.donusaccountbank.service.CalculationBalanceService;
import br.com.donus.donusaccountbank.service.PreCommitTransaction;
import br.com.donus.donusaccountbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService, CalculationBalanceService {

    private final TransactionPersistence transactionPersistence;
    private final PreCommitTransaction preCommitTransaction;
    private final LRUCache<String, BalanceProjection> balanceCached = new LRUCache<>(10);

    @Override
    public BalanceProjection commitTransactionAndRetrieveNewBalance(final TransactionWrapper newTransactions,
                                                                    final String cpfOwner) {
        final BalanceProjection balanceProjection = this.preCommitTransaction.validate(newTransactions, cpfOwner);

        this.transactionPersistence.saveAll(newTransactions);
        this.balanceCached.put(cpfOwner, balanceProjection);

        return balanceProjection;
    }

    @Override
    public BalanceProjection calculate(String cpfOwner) {
        return Optional.ofNullable(this.balanceCached.get(cpfOwner))
                .orElse(this.transactionPersistence.calculate(cpfOwner));
    }
}
