package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.repository.AccountingEntryRepository;
import com.fath.comptapoc.services.impl.AccountingEntryServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountingEntryServiceUnitTest {

    @InjectMocks
    AccountingEntryServiceImpl accountingEntryService;

    @Mock
    private Validator validator;

    @Mock
    private AccountingEntryRepository accountingEntryRepository;

    @Mock
    AccountingEntry accountingEntry;

    @Test
    void getAccountingEntryById_whenNotFound(){
        try{
            //when
            when( accountingEntryRepository.findById( anyLong() ) )
                    .thenReturn( Optional.empty() );
            //then
            assertThrows(
                    ClassNotFoundException.class,
                    () -> accountingEntryService.getAccountingEntryById( anyLong() ),
                    "Expected notFoundException to throw, but it didn't"
            );
        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void getAccoutingEntryById_whenFoundIt(){
        try{
            //when
            when( accountingEntry.getLabel() ).thenReturn( "test_label" );
            when( accountingEntryRepository.findById( anyLong() ) )
                    .thenReturn( Optional.of( accountingEntry ) );

            //then
            assertEquals(
                    accountingEntryService.getAccountingEntryById( anyLong() ).getLabel(),
                    accountingEntry.getLabel()
            );

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void getAllAccountingEntry_whenEmptyList(){
        //when
        when( accountingEntryRepository.findAll() ).thenReturn( Set.of() );

        //then
        try{
            assertTrue(
                    () -> accountingEntryService.getAll().isEmpty()
            );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    void getAllAccountingEntry_whenNotEmptyList(){
        //when
        when( accountingEntryRepository.findAll() ).thenReturn(
                Set.of(
                        mock( AccountingEntry.class ),
                        mock( AccountingEntry.class ),
                        mock( AccountingEntry.class )
                )
        );

        //then
        try{
            assertEquals(
                    accountingEntryService.getAll().size(),
                    3
            );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    void deleteAccountingEntry(){
        //then
        try{
            accountingEntryService.deleteAccountingEntry( anyLong() );
        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void saveAccountingEntry_WhenUnvalidAccouningtEntry(){
        //given
        ConstraintViolation labelConstraintViolationMock = mock( ConstraintViolation.class );
        when( labelConstraintViolationMock.getMessage() ).thenReturn( "Label is mandatory" );
        ConstraintViolation dateConstraintViolationMock = mock( ConstraintViolation.class );
        when( dateConstraintViolationMock.getMessage() ).thenReturn( "Date is mandatory" );
        ConstraintViolation bookConstraintViolationMock = mock (ConstraintViolation.class );
        when( bookConstraintViolationMock.getMessage() ).thenReturn( "AccountingBook is mandatory" );

        //when
        when( validator.validate( accountingEntry ) ).thenReturn( Set.of(
                labelConstraintViolationMock,
                dateConstraintViolationMock,
                bookConstraintViolationMock
        ));

        //then
        try {
            accountingEntryService.saveAccountingEntry(accountingEntry);
        } catch ( ConstraintViolationException e ){
            assertTrue( e.getMessage().contains("Label is mandatory"), "Expected message not thrown: Label is mandatory" );
            assertTrue( e.getMessage().contains("AccountingBook is mandatory"), "Expected message not thrown: AccountingBook is mandatory" );
            assertTrue( e.getMessage().contains("Date is mandatory"), "Expected message not thrown: Date is mandatory" );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            fail( e.getMessage() );
        }

    }

    @Test
    void saveAccountingEntry_whenValidAccountingEntry(){
        try{
            accountingEntryService.saveAccountingEntry( accountingEntry );
        } catch (Exception e ){
            fail( e.getMessage() );
        }

    }
}
