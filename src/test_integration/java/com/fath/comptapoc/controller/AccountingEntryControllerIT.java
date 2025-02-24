package com.fath.comptapoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.dtos.AccountingEntryDto;
import com.fath.comptapoc.model.dtos.FinancialAccountDto;
import com.fath.comptapoc.model.dtos.OperationDto;
import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
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
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountingEntryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    @Order(1)
    public void whenCreateAccountingEntryHaventAccountingBook_thenBadRequest() throws Exception {

        //give
        AccountingEntryDto accountingEntry = new AccountingEntryDto();
        accountingEntry.setLabel( "label_test_1" );
        accountingEntry.setDate( LocalDateTime.now() );

        //when //then
        mockMvc.perform(post("/accountingEntry/create")
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
        //give//when//then
        createAccountingEntryAndGiveHisId(
                "test_label_1",
                "test_book_name_1",
                createAndGiveAFinancialAccountDto( "account_name_1", 555)
        );
    }

    @Test
    @Order(4)
    @Transactional
    public void whenGetAccountingEntryById_thenIsFounded() throws Exception {

        //give
        AccountingEntryDto accountingEntryDto = createAccountingEntryAndGiveHisId(
                "test_label_2",
                "test_book_name_2",
                createAndGiveAFinancialAccountDto( "account_name_2", 555)
        );

        MvcResult result = mockMvc.perform( post("/accountingEntry/create")
                .content( new ObjectMapper().writeValueAsString( accountingEntryDto ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isCreated() )
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Integer id = JsonPath.parse( response ).read("$.id");

        //when //then
        mockMvc.perform( get("/accountingEntry/" + id ) )
                .andExpect( status().isFound() )
                .andExpect( jsonPath( "$.id").value( id ) )
                .andExpect( jsonPath("$.label").value( accountingEntryDto.getLabel() ) )
                .andExpect( jsonPath( "$.accountingBook.name").value( accountingEntryDto.getAccountingBook().getName() ) );

    }

    @Test
    @Order(5)
    @Transactional
    public void whenDeleteAccountingEntryById_thenIsDeleted() throws Exception {
        //give
        AccountingEntryDto accountingEntryDto = createAccountingEntryAndGiveHisId(
                "test_label_5",
                "test_book_name_5",
                createAndGiveAFinancialAccountDto( "account_name_5", 555)
        );

        //when //then
        mockMvc.perform( delete("/accountingEntry/" + accountingEntryDto.getId() ) )
                .andExpect( status().isOk() );
        //then
        mockMvc.perform( get("/accountingEntry/" + accountingEntryDto.getId() ) )
                .andExpect( status().isNotFound() );
    }

    private AccountingEntryDto createAccountingEntryAndGiveHisId( String label, String bookName, FinancialAccountDto financialAccountDto) throws Exception {
        AccountingBookDto accountingBookDto = createAndGiveAnAccountingBookDto( bookName );
        AccountingEntryDto accountingEntryDto = giveAnAccountingEntryDto(
                label,
                LocalDateTime.now(),
                accountingBookDto
        );
        addOperationDtoIntoEntry( 450, financialAccountDto, accountingEntryDto );
        addOperationDtoIntoEntry( -450, financialAccountDto, accountingEntryDto );

        MvcResult result = mockMvc.perform( post("/accountingEntry/create" )
                .content( new ObjectMapper().writeValueAsString( accountingEntryDto ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
            .andExpect( status().isCreated() )
            .andExpect( jsonPath("$.id" ).exists() )
            .andExpect( jsonPath( "$.label" ).value( accountingEntryDto.getLabel() ) )
            .andExpect( jsonPath( "$.date" ).value( accountingEntryDto.getDate().format( formatter ) ))
            .andExpect( jsonPath("$.accountingBook.name" ).value( accountingEntryDto.getAccountingBook().getName() ) )
            .andExpect( jsonPath( "$.operations").isNotEmpty() )
            .andReturn();

        String response = result.getResponse().getContentAsString();
        Integer id = JsonPath.parse( response ).read("$.id");

        accountingEntryDto.setId( id.longValue() );

        return accountingEntryDto;
    }

    private AccountingEntryDto giveAnAccountingEntryDto( String label, LocalDateTime localDateTime, AccountingBookDto accountingBookDto ){
        AccountingEntryDto accountingEntryDto = new AccountingEntryDto();
        accountingEntryDto.setLabel( label );
        accountingEntryDto.setDate( localDateTime );
        accountingEntryDto.setAccountingBook( accountingBookDto );
        return accountingEntryDto;

    }

    private AccountingBookDto giveAnAccountingBookDto(String name ){
        AccountingBookDto accountingBookDto = new AccountingBookDto();
        accountingBookDto.setName( name );
        return accountingBookDto;
    }

    private AccountingBookDto createAndGiveAnAccountingBookDto( String bookName ) throws Exception {

        AccountingBookDto accountingBookDto = giveAnAccountingBookDto( bookName );

        MvcResult mvcResult = mockMvc.perform( post("/accountBook/create")
                        .content( new ObjectMapper().writeValueAsString( accountingBookDto) )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                ).andExpect( status().isCreated() )
                .andExpect( jsonPath("$.id").exists() )
                .andExpect( jsonPath( "$.name").value( accountingBookDto.getName() ) )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.parse( response ).read("$.id");

        accountingBookDto.setId( id.longValue() );
        return accountingBookDto;
    }

    private FinancialAccountDto createAndGiveAFinancialAccountDto(String accountName, int code) throws Exception {
        FinancialAccountDto financialAccountDto = new FinancialAccountDto();
        financialAccountDto.setName( accountName );
        financialAccountDto.setCode( code );

        MvcResult mvcResult = mockMvc.perform( post("/financialAccount/create")
                .content( new ObjectMapper().writeValueAsString( financialAccountDto ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isCreated() )
                .andExpect( jsonPath( "$.id").exists() )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.parse( response ).read("$.id");

        financialAccountDto.setId( id.longValue() );
        return financialAccountDto;
    }

    private void addOperationDtoIntoEntry( int amount, FinancialAccountDto financialAccountDto, AccountingEntryDto accountingEntryDto ){
        OperationDto operationDto = new OperationDto();
        operationDto.setAmount( amount );
        operationDto.setFinancialAccount( financialAccountDto );
        accountingEntryDto.getOperations().add( operationDto );
    }
}
