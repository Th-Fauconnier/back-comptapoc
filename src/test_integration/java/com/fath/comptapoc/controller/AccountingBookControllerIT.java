package com.fath.comptapoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.dtos.PutAccountingBookDto;
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
    public void whenGetAccountingBookById_thenIsOk() throws Exception {
        //give
        Integer id = createAccountingBookAndGiveHisId( "label_test_1" );

        //then //when
        mockMvc.perform( get("/accountBook/" + id ))
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.id").value( id ))
                .andExpect( jsonPath( "$.name").value(  "label_test_1"));
    }

    @Test
    @Order(5)
    public void whenDeleteAccountingBookById_thenIsDeleted() throws Exception {
        //give
        Integer id = createAccountingBookAndGiveHisId( "label_test_2" );

        //when //then
        mockMvc.perform( delete("/accountBook/" + id))
                .andExpect( status().isOk() );
        //then
        mockMvc.perform( get("/accountBook/" + id))
                .andExpect( status().isNotFound() );
    }

    @Test
    @Order(6)
    public void whenGetAllAccountingBook_thenIsOk(){
        //todo
        //give
        Set<AccountingBookDto> accountingBookDtos = Set.of(
                giveAnAccountingBookDto( "book_1"),
                giveAnAccountingBookDto( "book_2"),
                giveAnAccountingBookDto( "book_3")
        );
    }

    @Test
    @Order(7)
    public void whenPutAccountingBook_giveMissingId_thenBadRequest() throws Exception {
        //give
        PutAccountingBookDto putAccountingBookDto = new PutAccountingBookDto();
        putAccountingBookDto.setName( "modifiedName_test_1");

        //when //then
        mockMvc.perform( put("/accountBook/update")
                .content( new ObjectMapper().writeValueAsString(putAccountingBookDto) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("$.message").value( "Id is mandatory") );

    }

    @Test
    @Order(8)
    public void whenPutAccountingBook_giveMissingName_thenBadRequest() throws Exception {
        //give
        PutAccountingBookDto putAccountingBookDto = new PutAccountingBookDto();
        putAccountingBookDto.setId( 55L );

        //when //then
        mockMvc.perform( put("/accountBook/update")
                        .content( new ObjectMapper().writeValueAsString(putAccountingBookDto) )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
                )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("$.message").value( "Name is mandatory") );

    }

    @Test
    @Order(9)
    @Transactional
    public void whenPutAccountingBook_giveValidDto_thenIsOk() throws Exception {
        //give
        Integer id = createAccountingBookAndGiveHisId( "bookName_01" );
        PutAccountingBookDto putAccountingBookDto = new PutAccountingBookDto();
        putAccountingBookDto.setId( id.longValue() );
        putAccountingBookDto.setName( "modified_bookName_01" );

        //when then
        mockMvc.perform( put("/accountBook/update" )
                .content( new ObjectMapper().writeValueAsString(putAccountingBookDto) )
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
