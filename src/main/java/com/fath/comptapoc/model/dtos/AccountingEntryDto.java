package com.fath.comptapoc.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude="accountingBook")
public class AccountingEntryDto {

    private Long id;

    @NotBlank(message = "Label is mandatory")
    private String label;

    //ModelMapper need JsonD&S, RequestBody need JsF&DateFormat
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Date is mandatory")
    private LocalDateTime date;

    @NotNull(message = "Book is mandatory")
    @JsonIgnoreProperties("accountingEntries")
    private AccountingBookDto accountingBook;

    @JsonIgnoreProperties("accountingEntry")
    private Set<OperationDto> operations = new HashSet<>();
}
