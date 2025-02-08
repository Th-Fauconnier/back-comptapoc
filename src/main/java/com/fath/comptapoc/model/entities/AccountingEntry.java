package com.fath.comptapoc.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fath.comptapoc.config.constraints.EntryBalance;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class AccountingEntry {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Label is mandatory")
    private String label;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Date is mandatory")
    private LocalDateTime date;

    @NotNull(message = "AccountingBook is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private AccountingBook accountingBook;

    @NotNull(message = "Operation list is mandatory")
    @OneToMany(mappedBy = "accountingEntry", cascade = CascadeType.PERSIST )
    @Size(min=2, message = "Operation list must have two or more operation" )
    @EntryBalance
    private List<@Valid Operation> operations = new ArrayList<>();
}
