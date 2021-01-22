package br.com.donus.donusaccountbank.web;

import br.com.donus.donusaccountbank.domain.Account;
import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.domain.tiny.Cpf;
import br.com.donus.donusaccountbank.service.AccountService;
import br.com.donus.donusaccountbank.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDTO> openAccount(@Valid @RequestBody CustomerDTO customer) {
        final Account account = this.accountService.openAccount(customer.toDomain());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccountDTO.fromDomain(account));
    }

    @PutMapping("/deposit")
    public ResponseEntity<BalanceDTO> deposit(@Valid @RequestBody DepositAmountDTO depositAmount) {
        final BalanceProjection deposit = this.accountService.deposit(
                new Cpf(depositAmount.getCpfOwner()).getValue(),
                depositAmount.getAmount());
        return ResponseEntity
                .ok(BalanceDTO.fromDomain(deposit));
    }

    @PutMapping("/transfer")
    public ResponseEntity<BalanceDTO> transfer(@Valid @RequestBody TransferAmountDTO transferAmount) {
        final BalanceProjection deposit = this.accountService.transfer(
                new Cpf(transferAmount.getCpfOwner()).getValue(),
                new Cpf(transferAmount.getCpfReceiver()).getValue(),
                transferAmount.getAmount());
        return ResponseEntity
                .ok(BalanceDTO.fromDomain(deposit));
    }

    @GetMapping("/balance/{cpfOwner}")
    public ResponseEntity<BalanceDTO> balance(@Valid @PathVariable("cpfOwner") String cpfOwner) {
        final BalanceProjection deposit = this.accountService.balance(new Cpf(cpfOwner).getValue());
        return ResponseEntity
                .ok(BalanceDTO.fromDomain(deposit));
    }
}
