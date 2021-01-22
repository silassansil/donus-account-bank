package br.com.donus.donusaccountbank.service;

import br.com.donus.donusaccountbank.domain.Account;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.domain.Customer;

public interface AccountService {

    Account openAccount(final Customer customer);

    BalanceProjection deposit(final String cpfOwner, final Double amount);

    BalanceProjection transfer(final String cpfOwner, final String cpfReceive, final Double amount);

    BalanceProjection balance(final String cpfOwner);
}
