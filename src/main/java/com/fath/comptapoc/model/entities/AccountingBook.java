package com.fath.comptapoc.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table
@Data
public class AccountingBook {

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "accountingBook")
    private Set<AccountingEntry> accountingEntries;

}
