package com.fath.comptapoc.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Table
@Data
public class AccountingBook {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name for accountingBook is mandatory")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountingBook", orphanRemoval = true)
    private List<AccountingEntry> accountingEntries;

}
