package com.fath.comptapoc.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Operation {

    @Id
    @GeneratedValue
    private Long id;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private FinancialAccount financialAccount;

    @ManyToOne
    @JoinColumn(name="entryId")
    private AccountingEntry accountingEntry;
  //  @ManyToOne
 //   @JoinColumn(name = "account_id", nullable = false)
 //   private FinancialAccount account;
}
