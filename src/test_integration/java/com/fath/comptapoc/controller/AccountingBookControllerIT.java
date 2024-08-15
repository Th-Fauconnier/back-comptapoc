package com.fath.comptapoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.AccountingBookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountingBookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private AccountingBookDto accountingBookDto;

//todo faire un objet dto pour l'ensemble du test
    @Test
    public void whenGetAccountingBook_thenNotFound() throws Exception {
        //when //then
        mockMvc.perform( get("/accountBook/14") )
                .andExpect( status().isNotFound() );
    }

    @Test
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
    public void whenCreateAccountingBook_thenIsCreated() throws Exception {
        //give
        AccountingBookDto accountingBookDto = new AccountingBookDto();
        accountingBookDto.setName( "label_test_1" );

        //when //then
        mockMvc.perform( post("/accountBook/create")
                        .content( new ObjectMapper().writeValueAsString( accountingBookDto) )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isCreated() )
                .andExpect( jsonPath("$.id").exists() )
                .andExpect( jsonPath( "$.name").value( accountingBookDto.getName() ));
    }

    @Test
    public void whenGetAccountingBookById_thenFoundIsFound() throws Exception {
        //give

        //then //when
        mockMvc.perform( get("/accountBook/1"))
                .andExpect( status().isFound() )
                .andExpect( jsonPath("$.id").value( 1L))
                .andExpect( jsonPath( "$.name").value(  "label_test_1"));
    }

    @Test
    public void whenDeleteAccountingBookById_thenIsDeleted() throws Exception {
        //give
        mockMvc.perform( get("/accountBook/1"))
                .andExpect( status().isFound() );
        //when //then
        mockMvc.perform(delete("/accountBook/1"))
                .andExpect( status().isOk() );
        //then
        mockMvc.perform( get("/accountBook/1"))
                .andExpect( status().isNotFound() );
    }
}
