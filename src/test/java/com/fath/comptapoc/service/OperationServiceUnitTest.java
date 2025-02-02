package com.fath.comptapoc.service;


import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.model.entities.Operation;
import com.fath.comptapoc.repository.OperationRepository;
import com.fath.comptapoc.services.impl.OperationServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationServiceUnitTest {

    @InjectMocks
    OperationServiceImpl operationService;

    @Mock
    private Validator validator;

    @Mock
    private OperationRepository operationRepository;

    @Test
    void saveOperation_whenInvalidOperation(){
        //given
        Operation operation = mock( Operation.class );
        ConstraintViolation nameConstraintViolationMock = mock(  ConstraintViolation.class );
        when( nameConstraintViolationMock.getMessage() ).thenReturn( "Amount is mandatory" );

        when( validator.validate(  operation ) )
                .thenReturn(
                        Set.of(
                                nameConstraintViolationMock
                        )
                );
        try{
            //when
            operationService.saveOperation( operation );

            //then
            fail( "ConstraintViolationException expected not throwing"  );
        } catch (ConstraintViolationException e ){
            assertThat( e.getMessage() ).isEqualTo( "Error occurred: Amount is mandatory. " );
        } catch ( Exception e )
        {
            System.out.println( e.getMessage() );
            fail( "Unexpected error");
        }
    }

    @Test
    void saveOperation_whenIsOk(){
        //given
        Operation operation = mock( Operation.class );

        //when//then
        try{
            operationService.saveOperation( operation );
        } catch (Exception e) {
            fail( e.getMessage() );
        }
    }

    @Test
    void getOperationById_whenNotFound(){
        //when
        when( operationRepository.findById( anyLong() ) )
                .thenReturn(Optional.empty() );
        //then
    //    try{
         //todo   operationService.
   //     }
    }
}
