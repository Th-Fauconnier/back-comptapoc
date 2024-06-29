package com.fath.comptapoc.model.mappers;

import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import org.mapstruct.Mapper;

@Mapper
public interface IAccountingBookMapper {

    AccountingBook convertToAccountingBook( AccountingBookDto accountingBookDto );

    AccountingBookDto convertToAccountingBookDto( AccountingBook accountingBook );
}
