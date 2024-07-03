package com.fath.comptapoc.controller;

import static org.awaitility.Awaitility.given;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = AccountingBookController.class )
public class AccountingBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountingBookServiceImpl accountingBookService;

    @MockBean
    private AccountingBook accountingBook;

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
        when( accountingBookService.getAccountingBookById( anyLong() ) ).thenReturn( Optional.empty() );

        //then
        mockMvc.perform( get( "/accountBook/14") )
                .andExpect( status().isNotFound() );
    }

    @Test
    public void getAccountingBookById_whenIsFound() throws  Exception {
        //when
        when( accountingBookService.getAccountingBookById( anyLong() ) )
                .thenReturn( Optional.of( accountingBook ) );

        //then
        mockMvc.perform( get( "/accountBook/14") )
                .andExpect( status().isFound() );
    }

    @Test
    public void getAllAccountingBook() throws Exception {
        //when
        when( accountingBookService.getAllAccountingBooks() )
                .thenReturn( List.of( accountingBook, accountingBook ));
        //then
        mockMvc.perform( get("/accountBook/all") )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$", hasSize(2) ) );
    }
}
