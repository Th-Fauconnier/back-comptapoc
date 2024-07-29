package com.fath.comptapoc.model.dtos;

import com.fath.comptapoc.model.entities.AccountingEntry;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountingEntryDtoITest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertAccountingEntryToAccountingEntryDto_thenCorrect() {
        AccountingEntry accountingEntry = new AccountingEntry();
        accountingEntry.setLabel( "LabelTest" );
        accountingEntry.setDate( new Date() );

        AccountingEntryDto accountingEntryDto = modelMapper.map( accountingEntry, AccountingEntryDto.class );

        assertEquals( accountingEntry.getLabel(), accountingEntryDto.getLabel() );
        assertEquals( accountingEntry.getDate(), accountingEntryDto.getDate() );
    }

    @Test
    public void whenConvertAccountingEntryDtoToAccountingEntry_thenCorrect() {
        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();
        accountingEntryDto.setLabel( "LabelTest" );
        accountingEntryDto.setDate( new Date() );

        AccountingEntry accountingEntry = modelMapper.map( accountingEntryDto, AccountingEntry.class );

        assertEquals( accountingEntry.getLabel(), accountingEntryDto.getLabel() );
        assertEquals( accountingEntry.getDate(), accountingEntryDto.getDate() );
    }
}
