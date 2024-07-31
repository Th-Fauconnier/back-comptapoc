package com.fath.comptapoc.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AccountingEntryDto {

    private Long id;

    private String label;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private AccountingBookDto accountingBookDto;

    private Set<OperationDto> operations;
}
