package br.com.donus.donusaccountbank.domain;

import br.com.donus.donusaccountbank.domain.tiny.Cpf;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Customer implements Serializable {

    private String id;
    private final String name;
    private final Cpf cpf;

    public Customer(final String name, final Cpf cpf) {
        this.name = name;
        this.cpf = cpf;
    }
}
