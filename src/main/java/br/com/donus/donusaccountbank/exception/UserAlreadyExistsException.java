package br.com.donus.donusaccountbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("user already exist for this cpf");
    }
}
