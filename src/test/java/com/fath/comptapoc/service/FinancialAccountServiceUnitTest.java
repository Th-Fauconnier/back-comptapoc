package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.repository.FinancialAccountRepository;
import com.fath.comptapoc.services.impl.FinancialAccountServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FinancialAccountServiceUnitTest {

    @InjectMocks
    FinancialAccountServiceImpl financialAccountService;

    @Mock
    private Validator validator;

    @Mock
    private FinancialAccountRepository financialAccountRepository;

    @Mock
    private FinancialAccount financialAccountMock;

    @Test
    void getFinancialAccountByName_whenNotFound(){
        try{
            //when
            when( financialAccountRepository.findByName( anyString() ) )
                    .thenReturn( Optional.empty() );
            //then
            assertThrows(
                    ClassNotFoundException.class,
                    () -> financialAccountService.getFinancialAccountByName( anyString() ),
                    "Expected notFoundException to throw, but it didn't"
            );
        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void getFinancialAccountByName_whenFoundIt(){
        try{
            //when
            when( financialAccountMock.getName() ).thenReturn( "test_name_2" );
            when( financialAccountRepository.findByName( anyString() ) )
                    .thenReturn( Optional.of( financialAccountMock ) );

            //then
            assertEquals(
                    financialAccountService.getFinancialAccountByName( anyString() ).getName(),
                    financialAccountMock.getName()
            );

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void getFinancialAccountById_whenNotFound(){
        try{
            //when
            when( financialAccountRepository.findById( anyLong() ) )
                    .thenReturn( Optional.empty() );
            //then
            assertThrows(
                    ClassNotFoundException.class,
                    () -> financialAccountService.getFinancialAccountById( anyLong() ),
                    "Expected notFoundException to throw, but it didn't"
            );
        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void getFinancialAccountById_whenFoundIt(){
        try{
            //when
            when( financialAccountMock.getName() ).thenReturn( "test_name_2" );
            when( financialAccountRepository.findById( anyLong() ) )
                    .thenReturn( Optional.of( financialAccountMock ) );

            //then
            assertEquals(
                    financialAccountService.getFinancialAccountById( anyLong() ).getName(),
                    financialAccountMock.getName()
            );

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void getAllFinancialAccount_whenEmptyList(){
        //when
        when( financialAccountRepository.findAll() ).thenReturn( List.of() );

        //then
        try{
            assertTrue(
                    () -> financialAccountService.getAllFinancialAccount().isEmpty()
            );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    void getAllFinancialAccount_whenNotEmptyList(){
        //when
        when( financialAccountRepository.findAll() ).thenReturn(
                List.of(
                        mock( FinancialAccount.class ),
                        mock( FinancialAccount.class ),
                        mock( FinancialAccount.class )
                )
        );

        //then
        try{
            assertEquals(
                    financialAccountService.getAllFinancialAccount().size(),
                    3
            );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    void deleteFinancialAccount_whenIsOk(){
        //then
        try{
            financialAccountService.deleteFinancialAccount( anyLong() );
        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void saveFinancialAccount_WhenIsUnvalid(){
        //given
        ConstraintViolation labelConstraintViolationMock = mock( ConstraintViolation.class );
        when( labelConstraintViolationMock.getMessage() ).thenReturn( "Name is mandatory" );
        ConstraintViolation dateConstraintViolationMock = mock( ConstraintViolation.class );
        when( dateConstraintViolationMock.getMessage() ).thenReturn( "Code is mandatory" );

        //when
        when( validator.validate( financialAccountMock ) ).thenReturn( Set.of(
                labelConstraintViolationMock,
                dateConstraintViolationMock
        ));

        //then
        try {
            financialAccountService.saveFinancialAccount( financialAccountMock );
        } catch ( ConstraintViolationException e ){
            assertTrue( e.getMessage().contains("Name is mandatory"), "Expected message not thrown: Name is mandatory" );
            assertTrue( e.getMessage().contains("Code is mandatory"), "Expected message not thrown: Code is mandatory" );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            fail( e.getMessage() );
        }

    }

    @Test
    void saveFinancialAccount_whenIsCreated(){
        try{
            financialAccountService.saveFinancialAccount( financialAccountMock );
        } catch( Exception e ){
            fail( e.getMessage() );
        }
    }
}
