package com.fath.comptapoc.model.dtos;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class AccountingEntryDto {

    private Long id;

    private String label;

    private Date date;

    private AccountingBookDto accountingBookDto;

    private Set<OperationDto> operations;
}
