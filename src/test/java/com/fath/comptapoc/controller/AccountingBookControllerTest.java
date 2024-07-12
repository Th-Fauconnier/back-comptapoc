package com.fath.comptapoc.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.AccountingBookDto;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@WebMvcTest(controllers = AccountingBookController.class )
public class AccountingBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountingBookServiceImpl accountingBookService;

    @MockBean
    private AccountingBookDto accountingBookDto;

    @Test
    public void testBadRoute() throws Exception {
        mockMvc.perform( get("/badRoute") )
                .andExpect( status().isNotFound() );
    }

    @Test
    public void testGoodRoute() throws Exception {
        mockMvc.perform( get("/accountBook/test") )
                .andExpect( status().isOk() );
    }

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
                .thenReturn( accountingBookDto );

        //then
        mockMvc.perform( get( "/accountBook/14") )
                .andExpect( status().isFound() );
    }

    @Test
    public void getAllAccountingBook_whenBooksFound() throws Exception {
        //when
        when( accountingBookService.getAllAccountingBooks() )
                .thenReturn( List.of( accountingBookDto, accountingBookDto ));
        //then
        mockMvc.perform( get("/accountBook/all") )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$", hasSize(2) ) );
    }

    @Test
    public void saveAccountingBook() throws Exception {
        when( accountingBookService.saveAccountingBook( accountingBookDto ) )
                .thenReturn( accountingBookDto );

        //todo
    }

    @Test
    public void getAllAccountingBook_whenNoBooksFound() throws Exception {
        //when
        when( accountingBookService.getAllAccountingBooks() )
                .thenReturn( List.of());
        //then
        mockMvc.perform( get("/accountBook/all") )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$", hasSize(0) ) );
    }

    @Test
    public void createAccountingBook_whenIsOk() throws Exception {

        when( accountingBookDto.getName() ).thenReturn("test_name");
        when( accountingBookDto.getId() ).thenReturn(14L );

        when( accountingBookService.saveAccountingBook( ArgumentMatchers.any( AccountingBookDto.class ) ) ).thenReturn( accountingBookDto );

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
}
