package br.com.donus.donusaccountbank;

import br.com.donus.donusaccountbank.domain.Account;
import br.com.donus.donusaccountbank.domain.Customer;
import br.com.donus.donusaccountbank.domain.Transaction;
import br.com.donus.donusaccountbank.domain.TransactionWrapper;
import br.com.donus.donusaccountbank.domain.enums.TransactionType;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.domain.tiny.Cpf;
import br.com.donus.donusaccountbank.web.dto.CustomerDTO;
import br.com.donus.donusaccountbank.web.dto.DepositAmountDTO;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public final class MockFactory {

    public static final String CPF_1 = "47362968019";
    public static final String CPF_2 = "11105331016";

    public static TransactionWrapper buildTransactionWrapper() {
        return new TransactionWrapper(
                MockFactory.buildTransaction(),
                MockFactory.buildTransaction(),
                MockFactory.buildTransaction(TransactionType.WITHDRAWAL)
        );
    }

    public static Customer buildCustomer() {
        return MockFactory.buildCustomer("Silas", new Cpf(CPF_1));
    }

    public static Customer buildCustomer(final String name, final Cpf cpf) {
        return new Customer(name, cpf);
    }

    public static Transaction buildTransaction() {
        return MockFactory.buildTransaction(TransactionType.DEPOSIT);
    }

    public static Transaction buildTransaction(final TransactionType type) {
        return new Transaction(MockFactory.buildCustomer(), 1000.0, type);
    }

    public static BalanceProjection buildBalanceProjection(final Double amount) {
        return new BalanceProjection(amount);
    }

    public static BalanceProjection buildBalanceProjection() {
        return MockFactory.buildBalanceProjection(1000.0);
    }

    public static CustomerDTO buildCustomerDTO() {
        return MockFactory.buildCustomerDTO("Silas");
    }

    public static Account buildAccount() {
        return new Account(MockFactory.buildCustomer(), LocalDate.now());
    }

    public static CustomerDTO buildCustomerDTO(final String name) {
        return MockFactory.buildCustomerDTO(name, CPF_1);
    }

    public static CustomerDTO buildCustomerDTO(final String name, final String cpf) {
        return CustomerDTO.fromDomain(MockFactory.buildCustomer(name, new Cpf(cpf)));
    }

    public static DepositAmountDTO buildDepositAmountDTO() {
        return MockFactory.buildDepositAmountDTO(CPF_2, 1000.0);
    }

    public static DepositAmountDTO buildDepositAmountDTO(final String cpfOwner, final Double amount) {
        return new DepositAmountDTO(amount, cpfOwner);
    }
}
