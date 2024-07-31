package com.fath.comptapoc.model.dtos;

import com.fath.comptapoc.model.entities.AccountingEntry;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountingEntryDtoITest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertAccountingEntryToAccountingEntryDto_thenCorrect() {
        AccountingEntry accountingEntry = new AccountingEntry();
        accountingEntry.setLabel( "LabelTest" );
        accountingEntry.setDate( LocalDateTime.now() );

        AccountingEntryDto accountingEntryDto = modelMapper.map( accountingEntry, AccountingEntryDto.class );

        assertEquals( accountingEntry.getLabel(), accountingEntryDto.getLabel() );
        assertEquals( accountingEntry.getDate(), accountingEntryDto.getDate() );
    }

    @Test
    public void whenConvertAccountingEntryDtoToAccountingEntry_thenCorrect() {
        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();
        accountingEntryDto.setLabel( "LabelTest" );
        accountingEntryDto.setDate( LocalDateTime.now() );

        AccountingEntry accountingEntry = modelMapper.map( accountingEntryDto, AccountingEntry.class );

        assertEquals( accountingEntry.getLabel(), accountingEntryDto.getLabel() );
        assertEquals( accountingEntry.getDate(), accountingEntryDto.getDate() );
    }
}
