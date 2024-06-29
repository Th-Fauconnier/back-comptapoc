package com.fath.comptapoc.model.mappers;

import com.fath.comptapoc.model.dtos.AccountingEntryDto;
import com.fath.comptapoc.model.entities.AccountingEntry;
import org.mapstruct.Mapper;

@Mapper
public interface IAccountingEntryMapper {

    AccountingEntry convertToAccountingEntry ( AccountingEntryDto accountingEntryDto );

    AccountingEntryDto convertToAccountingEntryDto( AccountingEntry accountingEntry );
}
