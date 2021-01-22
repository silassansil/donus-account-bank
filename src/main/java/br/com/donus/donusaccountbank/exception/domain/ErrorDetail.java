package br.com.donus.donusaccountbank.exception.domain;

import br.com.donus.donusaccountbank.exception.enums.ErrorKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ErrorDetail implements Serializable {

    private final ErrorKey error;
    private final String message;
}
