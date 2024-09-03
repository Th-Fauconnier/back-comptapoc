package com.fath.comptapoc.model.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PutAccountingEntryDto extends  AccountingEntryDto{

    @NotNull(message = "Id is mandatory")
    private Long id;
}
