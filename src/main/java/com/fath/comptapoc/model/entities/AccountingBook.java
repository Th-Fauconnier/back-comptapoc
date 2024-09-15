package com.fath.comptapoc.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class AccountingBook {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @OneToMany(mappedBy = "accountingBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccountingEntry> accountingEntries = new HashSet<>();

}
