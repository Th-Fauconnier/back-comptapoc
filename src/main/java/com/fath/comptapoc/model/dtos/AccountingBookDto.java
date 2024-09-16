package com.fath.comptapoc.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude="accountingEntries")
public class AccountingBookDto {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @JsonIgnoreProperties("accountingBook")
    private Set<AccountingEntryDto> accountingEntries;
}
