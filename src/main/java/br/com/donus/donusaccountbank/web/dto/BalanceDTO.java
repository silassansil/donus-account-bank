package br.com.donus.donusaccountbank.web.dto;

import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class BalanceDTO implements Serializable {

    private final Double balance;

    public static BalanceDTO fromDomain(final BalanceProjection balanceProjection) {
        return new BalanceDTO(balanceProjection.getAmount());
    }
}
