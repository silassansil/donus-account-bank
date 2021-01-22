package br.com.donus.donusaccountbank.persistence.entity;

import br.com.donus.donusaccountbank.domain.Account;
import br.com.donus.donusaccountbank.domain.behavior.MappableToDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity implements Serializable, MappableToDomain<Account> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private CustomerEntity customer;
    private LocalDate createdAt;

    public AccountEntity(CustomerEntity customer) {
        this.customer = customer;
        this.createdAt = LocalDate.now();
    }

    @Override
    public Account toDomain() {
        return new Account(this.customer.toDomain(), this.createdAt);
    }

    public static AccountEntity fromDomain(final Account account) {
        final CustomerEntity customer = CustomerEntity.fromDomain(account.getCustomer());
        return new AccountEntity(customer);
    }
}
