package br.com.donus.donusaccountbank.service.impl;

import br.com.donus.donusaccountbank.domain.Account;
import br.com.donus.donusaccountbank.domain.Customer;
import br.com.donus.donusaccountbank.domain.TransactionWrapper;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.persistence.AccountPersistence;
import br.com.donus.donusaccountbank.service.AccountService;
import br.com.donus.donusaccountbank.service.CalculationBalanceService;
import br.com.donus.donusaccountbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountPersistence accountPersistence;
    private final TransactionService transactionService;
    private final CalculationBalanceService calculationBalanceService;

    @Override
    public Account openAccount(Customer customer) {
        final Account newAccount = new Account(customer);
        return this.accountPersistence.persistNewAccount(newAccount);
    }

    @Override
    public BalanceProjection deposit(final String cpfOwner, final Double amount) {
        final Account account = this.accountPersistence.findAccountEntityByCustomerCpf(cpfOwner);
        final TransactionWrapper depositTransaction = account.deposit(amount);

        return this.transactionService.commitTransactionAndRetrieveNewBalance(depositTransaction, cpfOwner);
    }

    @Override
    public BalanceProjection transfer(final String cpfOwner, final String cpfReceive, final Double amount) {
        final Account accountOwner = this.accountPersistence.findAccountEntityByCustomerCpf(cpfOwner);
        final Account accountReceiver = this.accountPersistence.findAccountEntityByCustomerCpf(cpfReceive);
        final TransactionWrapper transferTransactions = accountOwner.transfer(amount, accountReceiver.getCustomer());

        return this.transactionService.commitTransactionAndRetrieveNewBalance(transferTransactions, cpfOwner);
    }

    @Override
    public BalanceProjection balance(String cpfOwner) {
        return this.calculationBalanceService.calculate(cpfOwner);
    }
}