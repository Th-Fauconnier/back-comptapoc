package com.fath.comptapoc.model.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class AccountingBookDto {

    private Long id;

    private String name;

    private Set<AccountingEntryDto> accountingEntries;
}
