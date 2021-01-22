package br.com.donus.donusaccountbank.persistence.entity;

import br.com.donus.donusaccountbank.domain.Transaction;
import br.com.donus.donusaccountbank.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private Double amount;

    @JoinColumn
    @ManyToOne
    private CustomerEntity owner;
    private TransactionType type;
    private LocalDate performedAt;

    public static TransactionEntity fromDomain(final Transaction transaction) {
        return TransactionEntity.builder()
                .amount(transaction.getAmount())
                .owner(CustomerEntity.fromDomain(transaction.getOwner()))
                .type(transaction.getType())
                .performedAt(transaction.getPerformedAt())
                .build();
    }
}
