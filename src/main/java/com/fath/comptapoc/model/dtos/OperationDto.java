package com.fath.comptapoc.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OperationDto {

    private Long id;

    @NotNull(message  = "Amount is mandatory")
    private int amount;

    @NotNull( message = "Account is mandatory")
    @JsonIgnoreProperties("operations")
    private FinancialAccountDto financialAccount;

}
