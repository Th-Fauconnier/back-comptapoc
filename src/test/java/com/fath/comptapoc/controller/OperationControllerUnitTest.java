package com.fath.comptapoc.controller;

import com.fath.comptapoc.config.security.WebConfigSecurity;
import com.fath.comptapoc.model.dtos.AccountingEntryDto;
import com.fath.comptapoc.model.dtos.FinancialAccountDto;
import com.fath.comptapoc.model.dtos.OperationDto;
import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.model.entities.Operation;
import com.fath.comptapoc.services.impl.OperationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OperationController.class)
@Import(WebConfigSecurity.class)
public class OperationControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationServiceImpl operationService;


    @Test
    public void getOperationById_whenNotFound(){
        try{
            //give
            when( operationService.getOperationById( anyLong() ) )
                    .thenThrow( ClassNotFoundException.class );

            //then//when
            mockMvc.perform( get("/operation/55") )
                    .andExpect( status().isNotFound() );
        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void getOperationById_WhenIsOk(){
        try{
            //given
            Operation operation = giveOperationMock(
                    -400,
                    mock( FinancialAccount.class ),
                    mock( AccountingEntry.class )
                    );
            //when
            when( operationService.getOperationById( anyLong() ) )
                    .thenReturn( operation );
            //then
            mockMvc.perform( get("/operation/45") )
                    .andExpect( status().isOk() );

        } catch(Exception e){
            fail(e.getMessage() );
        }
    }

    private Operation giveOperationMock(int amount, FinancialAccount account, AccountingEntry entry){
        Operation operation = mock( Operation.class );
        when( operation.getFinancialAccount() ).thenReturn( account );
        when( operation.getAccountingEntry() ).thenReturn( entry );
        return operation;
    }

    private OperationDto giveOperationDtoMock(int amount, FinancialAccountDto account, AccountingEntryDto entry){
        OperationDto operationDto = mock( OperationDto.class );
        when( operationDto.getFinancialAccount() ).thenReturn( account );
   //     when( operationDto.getAccountingEntry() ).thenReturn( entry );
        return operationDto;
    }
}
