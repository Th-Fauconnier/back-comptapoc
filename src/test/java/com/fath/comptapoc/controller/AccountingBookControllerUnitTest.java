package com.fath.comptapoc.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.model.dtos.PutAccountingBookDto;
import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

@WebMvcTest(AccountingBookController.class )
public class AccountingBookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountingBookServiceImpl accountingBookService;

    @MockBean
    private AccountingBookDto accountingBookDto;

    @MockBean
    private AccountingBook accountingBook;

    @Test
    public void getAccountingBookById_whenNotFound() throws  Exception {
        //when
        when( accountingBookService.getAccountingBookById( anyLong() ) )
                .thenThrow( ClassNotFoundException.class );

        //then
        mockMvc.perform( get( "/accountBook/14") )
                .andExpect( status().isNotFound() );
    }

    @Test
    public void getAccountingBookById_whenIsFound() throws  Exception {
        //when
        when( accountingBookService.getAccountingBookById( anyLong() ) )
                .thenReturn( accountingBook );

        //then
        mockMvc.perform( get( "/accountBook/14") )
                .andExpect( status().isFound() );
    }

    @Test
    public void getAllAccountingBook_whenBooksFound() throws Exception {
        //when
        AccountingBook mockedAccount_1 = mock(AccountingBook.class);
        when( mockedAccount_1.getName() ).thenReturn( "mockedAccount_1");
        AccountingBook mockedAccount_2 = mock(AccountingBook.class);
        when( mockedAccount_2.getName() ).thenReturn( "mockedAccount_2");

        Set< AccountingBook > accountingBookSet = Set.of( mockedAccount_1, mockedAccount_2 );
        when( accountingBookService.getAllAccountingBooks() )
                .thenReturn( accountingBookSet );
        //then
        mockMvc.perform( get("/accountBook/all") )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$", hasSize(2) ) );
    }

    @Test
    public void getAllAccountingBook_whenNoBooksFound() throws Exception {
        //when
        when( accountingBookService.getAllAccountingBooks() )
                .thenReturn( Set.of());
        //then
        mockMvc.perform( get("/accountBook/all") )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$", hasSize(0) ) );
    }

    @Test
    public void createAccountingBook_whenIsOk() throws Exception {
        when( accountingBookDto.getName() ).thenReturn("test_name");
        when( accountingBookDto.getId() ).thenReturn(14L );
        when( accountingBook.getName() ).thenReturn("test_name");
        when( accountingBook.getId() ).thenReturn(14L );

        when( accountingBookService.saveAccountingBook( any( AccountingBook.class ) ) ).thenReturn( accountingBook );

        //then
        ResultActions r = mockMvc.perform(
            post("/accountBook/create")
                    .content( new ObjectMapper().writeValueAsString( accountingBookDto ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
        )
            .andExpect( status().isCreated() )
            .andExpect( jsonPath("$.id").value( 14L ) )
            .andExpect( jsonPath("$.name").value("test_name") );
    }

    @Test
    public void createAccountingBook_whenBadRequest() throws Exception {

        when( accountingBookDto.getName() ).thenReturn(null);

        //then
        ResultActions r = mockMvc.perform(
                        post("/accountBook/create")
                                .content( new ObjectMapper().writeValueAsString( accountingBookDto ) )
                                .contentType( MediaType.APPLICATION_JSON )
                                .accept( MediaType.APPLICATION_JSON )
                )
                .andExpect( status().isBadRequest() )
                .andExpect( jsonPath("$.message").value( "Name is mandatory") );
    }

    @Test
    public void putAccountingBook_whenIsOk(){
        try{
            PutAccountingBookDto putAccountingBookDto = mock( PutAccountingBookDto.class );
            when( putAccountingBookDto.getName() ).thenReturn( "book_name" );
            when( putAccountingBookDto.getId() ).thenReturn( 44L );

            when( accountingBookService.saveAccountingBook( any( AccountingBook.class ) ) )
                    .thenReturn( accountingBook );

            mockMvc.perform( put("/accountBook/update" )
                    .content( new ObjectMapper().writeValueAsString( putAccountingBookDto ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            )
                    .andExpect( status().isOk() );

        } catch ( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenPutAccountingBook_thenBadRequest(){
        try{
            //give
            PutAccountingBookDto putAccountingBookDto = mock( PutAccountingBookDto.class );

            //when then
            mockMvc.perform( put("/accountBook/update")
                    .content( new ObjectMapper().writeValueAsString( putAccountingBookDto ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            )
                    .andExpect( status().isBadRequest() );

        } catch ( Exception e ){
            fail( e.getMessage() );
        }
    }
}
