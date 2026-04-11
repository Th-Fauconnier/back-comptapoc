package com.fath.comptapoc.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FinancialAccount {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name for a financial account is mandatory")
    private String name;

    private int code;

    @OneToMany(mappedBy = "financialAccount")
    private List<Operation> operations;
}
