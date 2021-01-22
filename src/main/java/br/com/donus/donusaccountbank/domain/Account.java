package br.com.donus.donusaccountbank.domain;

import br.com.donus.donusaccountbank.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Account implements Serializable {

    private final Customer customer;
    private final LocalDate createdAt;

    public Account(Customer customer) {
        this.customer = customer;
        this.createdAt = LocalDate.now();
    }

    public TransactionWrapper deposit(final Double amount) {
        return new TransactionWrapper(
                new Transaction(this.customer, amount, TransactionType.DEPOSIT));
    }

    public TransactionWrapper transfer(final Double amount, final Customer receiver) {
        return new TransactionWrapper(
                new Transaction(this.customer, amount, TransactionType.WITHDRAWAL),
                new Transaction(receiver, amount, TransactionType.DEPOSIT));

    }
}
