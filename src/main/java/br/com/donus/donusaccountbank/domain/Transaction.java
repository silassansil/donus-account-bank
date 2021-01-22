package br.com.donus.donusaccountbank.domain;

import br.com.donus.donusaccountbank.domain.enums.TransactionType;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class Transaction implements Serializable {

    private final Double amount;
    private final Customer owner;
    private final TransactionType type;
    private final LocalDate performedAt;

    public Transaction(Customer owner, Double amount, TransactionType type) {
        this.owner = owner;
        this.type = type;
        this.amount = type.transformAmountValue(amount);
        this.performedAt = LocalDate.now();
    }

    public String retrieverCpfOwner() {
        return this.getOwner().getCpf().getValue();
    }
}
