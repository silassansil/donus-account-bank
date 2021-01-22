package br.com.donus.donusaccountbank.web.dto;

import br.com.donus.donusaccountbank.domain.Customer;
import br.com.donus.donusaccountbank.domain.behavior.MappableToDomain;
import br.com.donus.donusaccountbank.domain.tiny.Cpf;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CustomerDTO implements MappableToDomain<Customer>, Serializable {

    @NotBlank
    private final String name;

    @NotBlank
    private final String cpf;

    @Override
    public Customer toDomain() {
        return new Customer(this.name, new Cpf(this.cpf));
    }

    public static CustomerDTO fromDomain(final Customer customer) {
        return new CustomerDTO(customer.getName(), customer.getCpf().toString());
    }
}
