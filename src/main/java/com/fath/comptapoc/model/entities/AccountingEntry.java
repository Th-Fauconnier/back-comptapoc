package com.fath.comptapoc.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountingEntry")
    private List<Operation> operations;
}
