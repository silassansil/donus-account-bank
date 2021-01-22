package br.com.donus.donusaccountbank.persistence.entity;

import br.com.donus.donusaccountbank.domain.Customer;
import br.com.donus.donusaccountbank.domain.behavior.MappableToDomain;
import br.com.donus.donusaccountbank.domain.tiny.Cpf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
public class CustomerEntity implements Serializable, MappableToDomain<Customer> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;
    private String cpf;

    @Override
    public Customer toDomain() {
        return new Customer(this.id, this.name, new Cpf(this.cpf));
    }

    public static CustomerEntity fromDomain(final Customer customer) {
        return new CustomerEntity(customer.getId(), customer.getName(), customer.getCpf().getValue());
    }
}
