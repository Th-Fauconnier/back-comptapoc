package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.Operation;
import com.fath.comptapoc.repository.OperationRepository;
import com.fath.comptapoc.services.impl.OperationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void getOperationById_whenNotFound(){
        //when
        when( operationRepository.findById( anyLong() ) )
                .thenReturn(Optional.empty() );
        //then
        try{
            assertThrows(
                    ClassNotFoundException.class,
                    () -> operationService.getOperationById( anyLong() ),
                    "Expected notFoundException to throw, but it didn't"
            );
        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void getAccoutingEntryById_whenFoundIt(){
        try{
            //given
            Operation operationMock = mock( Operation.class );
            //when
            when( operationRepository.findById( anyLong() ) )
                    .thenReturn( Optional.of( operationMock ) );

            //then
            assertNotNull(
                    operationService.getOperationById( anyLong() )
            );

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }
}
