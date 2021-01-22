package br.com.donus.donusaccountbank.web.dto;

import lombok.Getter;

@Getter
public class TransferAmountDTO extends DepositAmountDTO {

    private final String cpfReceiver;

    public TransferAmountDTO(final Double amount, final String cfpOwner, final String cpfReceiver) {
        super(amount, cfpOwner);
        this.cpfReceiver = cpfReceiver;
    }
}
