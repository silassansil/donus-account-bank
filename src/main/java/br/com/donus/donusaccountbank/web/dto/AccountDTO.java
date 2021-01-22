package br.com.donus.donusaccountbank.web.dto;

import br.com.donus.donusaccountbank.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AccountDTO implements Serializable {

    private final CustomerDTO customer;
    private final LocalDate createdAt;

    public static AccountDTO fromDomain(final Account account) {
        final CustomerDTO customerDTO = CustomerDTO.fromDomain(account.getCustomer());
        return new AccountDTO(customerDTO, account.getCreatedAt());
    }
}
