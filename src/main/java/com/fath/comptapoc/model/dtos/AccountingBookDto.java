package com.fath.comptapoc.model.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AccountingBookDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private Set<AccountingEntryDto> accountingEntries = new HashSet<>();
}
