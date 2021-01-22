package br.com.donus.donusaccountbank.domain.tiny;

import br.com.donus.donusaccountbank.exception.InsufficientFundsException;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class BalanceProjection implements Serializable {

    private final Double amount;

    public BalanceProjection(Double amount) {
        if (amount < 0) throw new InsufficientFundsException();
        this.amount = amount;
    }

    public BalanceProjection addAmount(final Double amount) {
        return new BalanceProjection(this.amount + amount);
    }
}
