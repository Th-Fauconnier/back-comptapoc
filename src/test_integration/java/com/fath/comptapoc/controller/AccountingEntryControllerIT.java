package com.fath.comptapoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.dtos.AccountingEntryDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountingEntryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void whenCreateAccountingEntryHaventAccountingBook_thenBadRequest() throws Exception {

        //give
        AccountingEntryDto accountingEntry = new AccountingEntryDto();
        accountingEntry.setLabel( "label_test_1" );
        accountingEntry.setDate( LocalDateTime.now() );

        //when //then
        ResultActions resultActions = mockMvc.perform(post("/accountingEntry/create")
                .content( new ObjectMapper().writeValueAsString( accountingEntry ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("$.message").value( "Book is mandatory") );
    }

    @Test
    @Order(1)
    public void whenCreateAccountingEntryHaventLabel_thenBadRequest() throws Exception {
        //give
        AccountingEntryDto accountingEntry = new AccountingEntryDto();
        accountingEntry.setDate( LocalDateTime.now() );
        accountingEntry.setAccountingBook( giveAnAccountingBookDto( "test_book_1" ));

        //when //then
        mockMvc.perform(post("/accountingEntry/create")
                        .content( new ObjectMapper().writeValueAsString( accountingEntry ) )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("$.message").value( "Label is mandatory") );
    }

    @Test
    @Order(1)
    public void whenCreateAccountingEntryHaventDate_thenBadRequest() throws Exception {
        //give
        AccountingEntryDto accountingEntry = new AccountingEntryDto();
        accountingEntry.setLabel( "label_test_1" );
        accountingEntry.setAccountingBook( giveAnAccountingBookDto( "test_book_1" ));

        //when //then
        mockMvc.perform(post("/accountingEntry/create")
                        .content( new ObjectMapper().writeValueAsString( accountingEntry ) )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("$.message").value( "Date is mandatory") );
    }

    @Test
    @Order(1)
    public void whenCreateAccountingEntry_thenIsCreated() throws Exception {
        //give
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.now();

        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();
        accountingEntryDto.setDate( date );
        accountingEntryDto.setLabel( "Test_label_1" );
        accountingEntryDto.setAccountingBook( giveAnAccountingBookDto("test_book_name" ) );

        System.out.println( "TEST" );
        System.out.println( accountingEntryDto );

        //when //then
        mockMvc.perform( post("/accountingEntry/create")
                .content( new ObjectMapper().writeValueAsString( accountingEntryDto ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isCreated() )
                .andExpect( jsonPath("$.id" ).exists() )
                .andExpect( jsonPath( "$.label" ).value( accountingEntryDto.getLabel() ) )
                .andExpect( jsonPath( "$.date" ).value( date.format( formatter ) ))
                .andExpect( jsonPath("$.accountingBook.name" ).value( accountingEntryDto.getAccountingBook().getName() ));
    }

    private AccountingBookDto giveAnAccountingBookDto( String name ){
        AccountingBookDto accountingBookDto = new AccountingBookDto();
        accountingBookDto.setName( name );
        return accountingBookDto;
    }
}
