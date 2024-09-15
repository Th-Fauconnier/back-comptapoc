package com.fath.comptapoc.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PutAccountingBookDto {

    @NotNull(message = "Id is mandatory")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
}
