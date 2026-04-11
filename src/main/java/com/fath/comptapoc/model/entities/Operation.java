package com.fath.comptapoc.model.entities;

import com.fath.comptapoc.config.constraints.OperationAmountFractional;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table
@Data
public class Operation {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message  = "Amount for operation is mandatory")
    private BigDecimal amount;

    @NotNull( message = "Financial account for operation is mandatory")
    @ManyToOne
    @JoinColumn(name = "accountId")
    private FinancialAccount financialAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="entryId")
    private AccountingEntry accountingEntry;
}
