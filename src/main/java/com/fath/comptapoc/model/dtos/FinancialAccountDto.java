package com.fath.comptapoc.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class FinancialAccountDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private int code;

    @JsonIgnoreProperties("financialAccount")
    private Set<OperationDto> operations;
}
