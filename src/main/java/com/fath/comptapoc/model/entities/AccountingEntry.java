package com.fath.comptapoc.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
@Data
public class AccountingEntry {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Label is mandatory")
    private String label;

    @NotNull(message = "Date is mandatory")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private AccountingBook accountingBook;

    @OneToMany(mappedBy = "accountingEntry")
    private Set<Operation> operations;
}
