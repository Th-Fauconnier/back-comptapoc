package com.fath.comptapoc.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class FinancialAccount {

    //todo faire les tests sur model/service/controller
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private int code;

    @OneToMany(mappedBy = "financialAccount")
    private Set<Operation> operations;
}
