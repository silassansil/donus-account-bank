package br.com.donus.donusaccountbank.persistence;

import br.com.donus.donusaccountbank.domain.Account;

public interface AccountPersistence {

    Account persistNewAccount(final Account account);

    Account findAccountEntityByCustomerCpf(final String cpfOwner);
}
