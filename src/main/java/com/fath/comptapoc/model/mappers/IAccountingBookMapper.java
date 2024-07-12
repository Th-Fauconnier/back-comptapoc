package com.fath.comptapoc.model.mappers;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface IAccountingBookMapper {

    AccountingBook convertToAccountingBook( AccountingBookDto accountingBookDto );

    AccountingBookDto convertToAccountingBookDto( AccountingBook accountingBook );

    Set<AccountingBookDto> mapToSetOfAccountingBookDto(Iterable<AccountingBook> accountingBooks);
}
