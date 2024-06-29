package com.fath.comptapoc.model.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class FinancialAccountDto {

    private Long id;
    private String name;
    private int code;

    private Set<OperationDto> operations;
}
