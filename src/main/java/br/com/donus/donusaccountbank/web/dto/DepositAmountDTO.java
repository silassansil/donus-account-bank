package br.com.donus.donusaccountbank.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class DepositAmountDTO implements Serializable {

    @NonNull
    @Min(0)
    @Max(2000)
    private final Double amount;

    @NotBlank
    private final String cpfOwner;
}
