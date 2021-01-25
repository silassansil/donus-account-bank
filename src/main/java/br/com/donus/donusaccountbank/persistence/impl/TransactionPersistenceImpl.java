package br.com.donus.donusaccountbank.persistence.impl;

import br.com.donus.donusaccountbank.domain.TransactionWrapper;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.persistence.TransactionPersistence;
import br.com.donus.donusaccountbank.persistence.entity.TransactionEntity;
import br.com.donus.donusaccountbank.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionPersistenceImpl implements TransactionPersistence {

    private final TransactionRepository transactionRepository;

    @Override
    public void saveAll(TransactionWrapper transactions) {
        this.transactionRepository.saveAll(transactions.getTransactions()
                .stream()
                .map(TransactionEntity::fromDomain)
                .collect(Collectors.toList()));
    }

    @Override
    public BalanceProjection calculate(final String cpfOwner) {
        return this.transactionRepository.calculateCurrentBalance(cpfOwner);
    }
}
