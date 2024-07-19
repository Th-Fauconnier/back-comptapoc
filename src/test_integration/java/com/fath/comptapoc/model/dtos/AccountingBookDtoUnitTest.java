package com.fath.comptapoc.model.dtos;

import com.fath.comptapoc.model.entities.AccountingBook;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountingBookDtoUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertAccountingBookEntityToAccountingBookDto_thenCorrect() {
        AccountingBook accountingBook = new AccountingBook();
        accountingBook.setName( "aName");
        accountingBook.setId( 45L );

        AccountingBookDto accountingBookDto = modelMapper.map( accountingBook, AccountingBookDto.class );
        assertEquals( accountingBook.getId(), accountingBookDto.getId() );
        assertEquals( accountingBook.getName(), accountingBookDto.getName() );
    }

    @Test
    public void whenConvertAccountingBookDtoToAccountingBookEntity_thenCorrect() {
        AccountingBookDto accountingBookDto = new AccountingBookDto();
        accountingBookDto.setName( "aName");
        accountingBookDto.setId( 45L );

        AccountingBook accountingBook = modelMapper.map( accountingBookDto, AccountingBook.class );
        assertEquals( accountingBook.getId(), accountingBookDto.getId() );
        assertEquals( accountingBook.getName(), accountingBookDto.getName() );
    }
}
