package com.fath.comptapoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.AccountingBookDto;
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

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountingBookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private Integer idFromCreatedBook;

    @Test
    @Order(1)
    public void whenGetAccountingBook_thenNotFound() throws Exception {
        //when //then
        mockMvc.perform( get("/accountBook/1") )
                .andExpect( status().isNotFound() );
    }

    @Test
    @Order(2)
    public void whenCreateAccountingBook_thenBadRequest() throws Exception {
        //give
        AccountingBookDto accountingBookDto = new AccountingBookDto();

        //when //then
        mockMvc.perform( post("/accountBook/create")
                .content( new ObjectMapper().writeValueAsString( accountingBookDto ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isBadRequest() );
    }

    @Test
    @Order(3)
    public void whenCreateAccountingBook_thenIsCreated() throws Exception {
        //give
        AccountingBookDto accountingBookDto = giveAnAccountingBookDto( "label_test_1" );

        //when //then
        mockMvc.perform( post("/accountBook/create")
                        .content( new ObjectMapper().writeValueAsString( accountingBookDto) )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
        ) .andExpect( status().isCreated() )
                .andExpect( jsonPath("$.id").exists() )
                .andExpect( jsonPath( "$.name").value( accountingBookDto.getName() ));
    }

    @Test
    @Order(4)
    @Transactional
    public void whenGetAccountingBookById_thenIsFound() throws Exception {
        //give
        Integer id = createAccountingBookAndGiveHisId( "label_test_1" );

        //then //when
        mockMvc.perform( get("/accountBook/" + id ))
                .andExpect( status().isFound() )
                .andExpect( jsonPath("$.id").value( id ))
                .andExpect( jsonPath( "$.name").value(  "label_test_1"));
    }

    @Test
    @Order(5)
    public void whenDeleteAccountingBookById_thenIsDeleted() throws Exception {
        //give
        Integer id = createAccountingBookAndGiveHisId( "label_test_2" );

        //when //then
        mockMvc.perform(delete("/accountBook/" + id))
                .andExpect( status().isOk() );
        //then
        mockMvc.perform( get("/accountBook/" + id))
                .andExpect( status().isNotFound() );
    }

    @Test
    @Order(6)
    public void whenGetAllAccountingBook_thenIsOk(){
        //give
        Set<AccountingBookDto> accountingBookDtos = Set.of(
                giveAnAccountingBookDto( "book_1"),
                giveAnAccountingBookDto( "book_2"),
                giveAnAccountingBookDto( "book_3")
        );
      //  accountingBookDtos.forEach( aBook -> createAccountingBookAndGiveHisId( aBook.getName() ));
    }

    @Test
    @Order(7)
    @Transactional
    public void whenPutAccountingBook_thenIsOk() throws Exception {
        //give
        Integer id = createAccountingBookAndGiveHisId( "bookName_01" );
        AccountingBookDto accountingBookDto = new AccountingBookDto();
        accountingBookDto.setId( id.longValue() );
        accountingBookDto.setName( "modified_bookName_01" );

        //when then
        mockMvc.perform( put("/accountBook/update" )
                .content( new ObjectMapper().writeValueAsString( accountingBookDto ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.id").value( id ) )
                .andExpect( jsonPath( "$.name").value( "modified_bookName_01") );

    }

    private Integer createAccountingBookAndGiveHisId( String bookName) throws Exception {
        AccountingBookDto accountingBookDto = giveAnAccountingBookDto( bookName );

        MvcResult result = mockMvc.perform( post("/accountBook/create" )
                .content( new ObjectMapper().writeValueAsString( accountingBookDto ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        ).andReturn();

        return JsonPath.read( result.getResponse().getContentAsString(), "$.id");
    }

    private AccountingBookDto giveAnAccountingBookDto( String bookName ){
        AccountingBookDto accountingBookDto = new AccountingBookDto();
        accountingBookDto.setName( bookName );
        return accountingBookDto;
    }
}
