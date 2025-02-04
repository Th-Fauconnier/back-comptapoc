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

    @NotNull( message = "AccountingEntry is mandatory")
    @ManyToOne
    @JoinColumn(name="entryId")
    private AccountingEntry accountingEntry;
}
