package com.fath.comptapoc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fath.comptapoc.model.dtos.FinancialAccountDto;
import com.fath.comptapoc.model.entities.FinancialAccount;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FinancialAccountControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenSaveFinancialAccount_thenIsCreated(){
        try{
            //given
            FinancialAccountDto financialAccountDto = giveFinancialAccountDto(
                    "account_name_1",
                    456
            );

            //when
            mockMvc.perform( post("/financialAccount/create")
                    .content( new ObjectMapper().writeValueAsString( financialAccountDto) )
                    .contentType(MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            )
                    .andExpect( status().isCreated() );
        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenSaveFinancialAccount_thenIsBadRequest(){
        try{
            //given //when //then
            mockMvc.perform( post("/financialAccount/create")
                            .content( new ObjectMapper().writeValueAsString( new FinancialAccountDto() ) )
                            .contentType(MediaType.APPLICATION_JSON )
                            .accept( MediaType.APPLICATION_JSON )
                    )
                    .andExpect( status().isBadRequest() );
        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenGetFinancialAccount_thenIsFound(){
        try{
            //given
            int accountId = createFinancialAccountAndGiveHisId( "account_name_2", 667 );

            //when //then
            mockMvc.perform( get("/financialAccount/" + accountId ) )
                    .andExpect( status().isFound() );

        } catch( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenGetFinancialAccount_thenIsNotFound(){
        try{
            //given //when //then
            mockMvc.perform( get("/financialAccount/5775454" ) )
                    .andExpect( status().isNotFound() );
        } catch (Exception e ){
            fail ( e.getMessage() );
        }
    }

    @Test
    public void whenUpdateFinancialAccount_thenIsBadRequest(){
        try{
            //given //when //then
            mockMvc.perform( put("/financialAccount/update")
                    .content( new ObjectMapper().writeValueAsString( new FinancialAccountDto() ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            ).andExpect( status().isBadRequest() );

        } catch ( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenUpdateFinancialAccount_thenIsOk(){
        try{
            //given
            Long idAccount = createFinancialAccountAndGiveHisId( "account_name_3", 551).longValue();
            FinancialAccountDto financialAccountDto = giveFinancialAccountDto( "account_name_modified", 697 );
            financialAccountDto.setId( idAccount );

            //when //then
            mockMvc.perform( put("/financialAccount/update")
                    .content( new ObjectMapper().writeValueAsString( financialAccountDto ) )
                    .contentType( MediaType.APPLICATION_JSON )
                    .accept( MediaType.APPLICATION_JSON )
            )
                    .andExpect( status().isOk() )
                    .andExpect( jsonPath( "$.id").value( idAccount ) )
                    .andExpect( jsonPath( "$.name").value( financialAccountDto.getName() ) )
                    .andExpect( jsonPath( "$.code").value( financialAccountDto.getCode() ) );

        } catch ( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenDeleteFinancialAccount_thenIsOk(){
        try{
            //given
            int accountId = createFinancialAccountAndGiveHisId( "account_name_4", 42875 );
            //when //then
            mockMvc.perform( delete("/financialAccount/" + accountId ) )
                    .andExpect( status().isOk() );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    private FinancialAccountDto giveFinancialAccountDto( String name, int code ){
        FinancialAccountDto financialAccountDto = new FinancialAccountDto();
        financialAccountDto.setCode( code );
        financialAccountDto.setName( name );
        return financialAccountDto;
    }

    private Integer createFinancialAccountAndGiveHisId( String name, int code ) throws Exception {
        FinancialAccountDto financialAccountDto = giveFinancialAccountDto( name, code );
        MvcResult mvcResult = mockMvc.perform( post("/financialAccount/create" )
                .content( new  ObjectMapper().writeValueAsString( financialAccountDto ) )
                .contentType( MediaType.APPLICATION_JSON )
                .accept( MediaType.APPLICATION_JSON )
        )
                .andExpect( status().isCreated() )
                .andReturn();

        return JsonPath.read( mvcResult.getResponse().getContentAsString(), "$.id" );
    }
}
