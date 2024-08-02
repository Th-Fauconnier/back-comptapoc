package com.fath.comptapoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.dtos.AccountingEntryDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.services.impl.AccountingEntryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AccountingEntryControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountingEntryServiceImpl accountingEntryService;

    @MockBean
    private AccountingEntryDto accountingEntryDto;

    @MockBean
    private AccountingEntry accountingEntry;

    @Test
    public void getAccountingEntryByID_thenNotFound(){
        try{
            //give
            when( accountingEntryService.getAccountingEntryById( anyLong() ) )
                    .thenThrow( ClassNotFoundException.class );

            //when //then
            mockMvc.perform( get("/accountingEntry/14") )
                    .andExpect( status().isNotFound() );

        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void getAccountingEntryByID_thenIsFound() {
        try{
            //give
            when( accountingEntryService.getAccountingEntryById( anyLong() ) )
                    .thenReturn( accountingEntry );

            //when //then
            mockMvc.perform( get("/accountingEntry/14" ) )
                    .andExpect( status().isFound() );

        } catch(Exception e){
            fail(e.getMessage() );
        }
    }

    @Test
    public void getAllEntries_thenEntriesFound(){
        try {
            //give
            AccountingBook accountingBookMock = giveAccountingBookMock("test_book_1");
            Set<AccountingEntry> accountingEntries = Set.of(
                    giveAccountingEntryMock("test_entry_1", accountingBookMock),
                    giveAccountingEntryMock("test_entry_2", accountingBookMock),
                    giveAccountingEntryMock("test_entry_3", accountingBookMock)
            );
            when( accountingEntryService.getAll() ).thenReturn( accountingEntries );

            //when //then
            mockMvc.perform( get("/accountingEntry/all") )
                    .andExpect( status().isOk() )
                    .andExpect( jsonPath("$", hasSize(3) ) );

        } catch (Exception e ){
            fail(e.getMessage() );
        }
    }

    @Test
    public void whenCreateAccountingEntry_thenIsOk(){
        try{
            //give
            AccountingEntryDto accountingEntryDtoMock = giveAccountingEntryDtoMock(
                    "test_entry_4",
                    giveAccountingBookDtoMock("test_book_2" )
            );
            when( accountingEntryDtoMock.getId() ).thenReturn( 14L );

            AccountingEntry accountingEntryMock = giveAccountingEntryMock(
                    "test_entry_4",
                    giveAccountingBookMock( "test_book_2")
            );
            when( accountingEntryMock.getId() ).thenReturn( 14L );

            when( accountingEntryService.saveAccountingEntry( any( AccountingEntry.class ) ) )
                    .thenReturn( accountingEntryMock );

            //when //then
            mockMvc.perform( post("/accountingEntry/create")
                    .content( new ObjectMapper().writeValueAsString( accountingEntryDtoMock ) )
                    .contentType(MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            )
                    .andExpect( status().isCreated() )
                    .andExpect( jsonPath("$.id").value( 14L ) )
                    .andExpect( jsonPath("$.label").value("test_entry_4") )
                    .andExpect( jsonPath( "$.accountingBook.name").value( "test_book_2" ) );

        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenCreateAccountingEntry_thenBadRequest(){
        try{
            //give
            AccountingEntryDto accountingEntryDto = mock( AccountingEntryDto.class );
            //when //then
            mockMvc.perform( post("/accountingEntry/create")
                    .content( new ObjectMapper().writeValueAsString( accountingEntryDto ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            ).andExpect( status().isBadRequest() );

        } catch(Exception e ){
            fail( e.getMessage());
        }
    }

    private AccountingBook giveAccountingBookMock(String name ){
        AccountingBook accountingBookMock = mock( AccountingBook.class );
        when( accountingBookMock.getName() ).thenReturn( name );
        return accountingBookMock;
    }

    private AccountingEntry giveAccountingEntryMock(String label, AccountingBook book){
        AccountingEntry accountingEntryMock = mock( AccountingEntry.class );
        when( accountingEntryMock.getLabel() ).thenReturn( label );
        when( accountingEntryMock.getAccountingBook() ).thenReturn( book );
        return accountingEntryMock;
    }

    private AccountingBookDto giveAccountingBookDtoMock(String name ){
        AccountingBookDto accountingBookMock = mock( AccountingBookDto.class );
        when( accountingBookMock.getName() ).thenReturn( name );
        return accountingBookMock;
    }

    private AccountingEntryDto giveAccountingEntryDtoMock(String label, AccountingBookDto book){
        AccountingEntryDto accountingEntryMock = mock( AccountingEntryDto.class );
        when( accountingEntryMock.getLabel() ).thenReturn( label );
        when( accountingEntryMock.getAccountingBookDto() ).thenReturn( book );
        return accountingEntryMock;
    }
}
