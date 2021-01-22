package br.com.donus.donusaccountbank.domain.tiny;

import br.com.donus.donusaccountbank.exception.InvalidCpfException;
import lombok.Getter;

import java.io.Serializable;

public class Cpf implements Serializable {

    @Getter
    private final String value;

    public Cpf(String value) {
        this.value = value.replaceAll("\\W", "");
        if (this.value.length() != 11) throw new InvalidCpfException();
    }

    @Override
    public String toString() {
        return this.value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
