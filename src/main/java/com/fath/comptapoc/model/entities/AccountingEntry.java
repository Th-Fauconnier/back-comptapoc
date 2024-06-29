package com.fath.comptapoc.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Table
@Data
public class AccountingEntry {

    @Id
    @GeneratedValue
    private Long id;

    private String label;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private AccountingBook accountingBook;

    @OneToMany(mappedBy = "accountingEntry")
    private Set<Operation> operations;
}
