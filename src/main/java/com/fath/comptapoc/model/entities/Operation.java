package com.fath.comptapoc.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table
@Data
public class Operation {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message  = "Amount is mandatory")
    private int amount;

    @NotNull( message = "Account is mandatory")
    @ManyToOne
    @JoinColumn(name = "accountId")
    private FinancialAccount financialAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="entryId")
    private AccountingEntry accountingEntry;
}
