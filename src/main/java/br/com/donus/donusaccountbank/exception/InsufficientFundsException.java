package br.com.donus.donusaccountbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("insufficient funds to perform this operation");
    }
}
