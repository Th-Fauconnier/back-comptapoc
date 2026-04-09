package com.fath.comptapoc.model.entities;

import com.fath.comptapoc.config.constraints.OperationAmount;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table
@Data
@OperationAmount
public class Operation {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message  = "Amount is mandatory")
    private BigDecimal amount;

    @NotNull( message = "Account is mandatory")
    @ManyToOne
    @JoinColumn(name = "accountId")
    private FinancialAccount financialAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="entryId")
    private AccountingEntry accountingEntry;
}
