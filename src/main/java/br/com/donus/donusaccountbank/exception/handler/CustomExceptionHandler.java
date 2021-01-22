package br.com.donus.donusaccountbank.exception.handler;

import br.com.donus.donusaccountbank.exception.InsufficientFundsException;
import br.com.donus.donusaccountbank.exception.InvalidCpfException;
import br.com.donus.donusaccountbank.exception.UserAlreadyExistsException;
import br.com.donus.donusaccountbank.exception.UserNotFoundException;
import br.com.donus.donusaccountbank.exception.domain.ErrorDetail;
import br.com.donus.donusaccountbank.exception.enums.ErrorKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetail> exceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorDetail(ErrorKey.USER_ALREADY_EXISTS, exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetail> exceptionHandler(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetail(ErrorKey.USER_NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorDetail> exceptionHandler(InsufficientFundsException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDetail(ErrorKey.INSUFFICIENT_FUNDS_IN_ACCOUNT, exception.getMessage()));
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ErrorDetail> exceptionHandler(InvalidCpfException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetail(ErrorKey.INVALID_CPF, exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetail> exceptionHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetail(ErrorKey.BODY_MISSING, "required request body is missing"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> exceptionHandler(MethodArgumentNotValidException exception) {
        final BindingResult result = exception.getBindingResult();
        final List<FieldError> fields = result.getFieldErrors();
        final FieldError firstField = fields.get(0);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetail(ErrorKey.INVALID_DATA,
                        firstField.getField() + " " + firstField.getDefaultMessage()));
    }
}
