package com.fath.comptapoc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.FinancialAccountDto;
import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.services.impl.FinancialAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinancialAccountController.class)
public class FinancialAccountControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialAccountServiceImpl financialAccountService;

    @Test
    public void whenCreatedFinancialAccount_thenIsCreated(){
        try{
            //given
            FinancialAccountDto financialAccountDtoMock = mock( FinancialAccountDto.class );
            when( financialAccountDtoMock.getName() ).thenReturn( "name_account" );
            when( financialAccountDtoMock.getCode() ).thenReturn( 55455 );
            FinancialAccount financialAccountMock = mock( FinancialAccount.class );
            when( financialAccountMock.getName() ).thenReturn( "name_account" );
            when( financialAccountMock.getCode() ).thenReturn( 55455 );
            when( financialAccountMock.getId() ).thenReturn( 5L );

            //when
            when( financialAccountService.saveFinancialAccount( any( FinancialAccount.class ) ) )
                    .thenReturn( financialAccountMock );

            //then
            mockMvc.perform( post("/financialAccount/create")
                    .content( new ObjectMapper().writeValueAsString( financialAccountDtoMock ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            ).andExpect( status().isCreated() );

        } catch ( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenFinancialAccountCreated_thenBadRequest(){
        try{
            //given
            FinancialAccountDto financialAccountDto = mock( FinancialAccountDto.class );

            //when //then
            mockMvc.perform( post("/financialAccount/create")
                    .content( new ObjectMapper().writeValueAsString( financialAccountDto ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            ).andExpect( status().isBadRequest() );
        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void getFinancialAccountById_whenNotFound(){
        try{
            //given
            when( financialAccountService.getFinancialAccountById( anyLong() ) )
                    .thenThrow( ClassNotFoundException.class );
            //then//when
            mockMvc.perform( get("/financialAccount/565") )
                    .andExpect( status().isNotFound() );

        } catch( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void getFinancialAccountById_IsFound(){
        try{
            //given
            FinancialAccount financialAccount = mock( FinancialAccount.class );

            when( financialAccountService.getFinancialAccountById( anyLong() ) )
                    .thenReturn( financialAccount );

            //then
            mockMvc.perform( get("/financialAccount/5465") )
                    .andExpect( status().isFound() );

        } catch( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void updateFinancialAccount_thenBadRequest(){
        try{
            //given
            FinancialAccountDto financialAccountDto = mock( FinancialAccountDto.class );

            //when //then
            mockMvc.perform( put("/financialAccount/update")
                    .content( new ObjectMapper().writeValueAsString( financialAccountDto ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            ).andExpect( status().isBadRequest() );
        } catch( Exception e ){
            fail(e.getMessage() );
        }
    }

    @Test
    public void updateFinancialAccount_thenIsOk(){
        try{
            //given
            FinancialAccountDto financialAccountDtoMock = mock( FinancialAccountDto.class );
            when( financialAccountDtoMock.getName() ).thenReturn( "name_account" );
            when( financialAccountDtoMock.getCode() ).thenReturn( 55455 );
            FinancialAccount financialAccountMock = mock( FinancialAccount.class );
            when( financialAccountMock.getName() ).thenReturn( "name_account" );
            when( financialAccountMock.getCode() ).thenReturn( 55455 );
            when( financialAccountMock.getId() ).thenReturn( 5L );

            //when
            when( financialAccountService.getFinancialAccountById( anyLong() ) )
                    .thenReturn( financialAccountMock );
            when( financialAccountService.saveFinancialAccount( any( FinancialAccount.class ) ) )
                    .thenReturn( financialAccountMock );

            //then
            mockMvc.perform( put("/financialAccount/update")
                    .content( new ObjectMapper().writeValueAsString( financialAccountDtoMock ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            ).andExpect( status().isOk() );
        } catch( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void deleteFinancialAccount_thenIsOk(){
        try{

            //when //then
            mockMvc.perform( delete("/financialAccount/55") )
                    .andExpect( status().isOk() );

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }
}
