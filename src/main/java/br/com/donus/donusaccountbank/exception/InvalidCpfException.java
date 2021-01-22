package br.com.donus.donusaccountbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCpfException extends RuntimeException {

    public InvalidCpfException() {
        super("cpf must have 11 chars");
    }
}
