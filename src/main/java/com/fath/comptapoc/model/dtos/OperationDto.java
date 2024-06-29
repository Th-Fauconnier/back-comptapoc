package com.fath.comptapoc.model.dtos;

import lombok.Data;

@Data
public class OperationDto {

    private Long id;

    private int amount;

    private FinancialAccountDto financialAccount;

    private AccountingEntryDto accountingEntry;
}
