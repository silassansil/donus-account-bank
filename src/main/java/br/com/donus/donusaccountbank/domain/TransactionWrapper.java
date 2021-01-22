package br.com.donus.donusaccountbank.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
public class TransactionWrapper implements Serializable {

    private final List<Transaction> transactions;

    public TransactionWrapper(Transaction... transactions) {
        this.transactions = Arrays.asList(transactions);
    }

    public Double calculateNewTransactionValues(final String cpfOwner) {
        return this.transactions.stream()
                .filter(transaction -> transaction.retrieverCpfOwner().equals(cpfOwner))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}
