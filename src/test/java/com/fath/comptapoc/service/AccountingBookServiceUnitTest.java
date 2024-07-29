package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.repository.AccountingBookRepository;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountingBookServiceUnitTest {

    @InjectMocks
    AccountingBookServiceImpl accountingBookService;

    @Mock
    private Validator validator;

    @Mock
    private AccountingBookRepository accountingBookRepository;

    @Mock
    private AccountingBook accountingBook;

    @Test
    void getAccountingBookById_whenNotFound(){
        //when
        when( accountingBookRepository.findById( anyLong() ) )
                .thenReturn(Optional.empty() );
        //then
        try{
            accountingBookService.getAccountingBookById( anyLong() );
            fail("NotFound Exception expected not throwing");

        } catch (Exception e) {
            assertThat( e.getMessage() ).isEqualTo( "AccountingBook not found" );
        }
    }

    @Test
    void getAccountingBookById_whenFoundIt() {
        //when
        when( accountingBook.getId() )
                .thenReturn( 21L );
        when( accountingBookRepository.findById( anyLong() ) )
                .thenReturn(Optional.of( accountingBook ) );
        //then
        try {
            accountingBookService.getAccountingBookById( anyLong() );
            assertThat( accountingBook.getId() ).isEqualTo( 21L );

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getAllAccountingBook_whenEmptyList(){
        //when
        when( accountingBookRepository.findAll() )
                .thenReturn( Set.of( ) );

        //then
        try{
            Set<AccountingBook> accountingBookSet = (Set<AccountingBook>) accountingBookRepository.findAll();

            assertThat( accountingBookSet.isEmpty() ).isTrue();

        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void getAllAccountingBook_whenNotEmptyList(){
        //when
        when( accountingBookRepository.findAll() )
                .thenReturn(
                        Set.of(
                                mock( AccountingBook.class ),
                                mock( AccountingBook.class ),
                                mock( AccountingBook.class )
                        )
                );

        //then
        try{
            Set<AccountingBook> accountingBookSet = (Set<AccountingBook>) accountingBookRepository.findAll();

            assertThat( accountingBookSet.size() ).isEqualTo( 3 );

        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void deleteAccountingBook(){
        //then
        try{
            accountingBookService.deleteAccountingBook( anyLong() );

        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void saveAccountingBook__whenUnvalidAccountingBook(){

        //given
        ConstraintViolation nameConstraintViolationMock = mock(  ConstraintViolation.class );
        when( nameConstraintViolationMock.getMessage() ).thenReturn( "Name is mandatory" );

        when( validator.validate(  accountingBook ) )
                .thenReturn(
                        Set.of(
                                nameConstraintViolationMock
                        )
                );

        try{
            //when
            accountingBookService.saveAccountingBook( accountingBook );

            //then
            fail( "ConstraintViolationException expected not throwing" );
        } catch ( ConstraintViolationException e ){
            assertThat( e.getMessage() ).isEqualTo( "Error occurred: Name is mandatory" );
        } catch ( Exception e )
        {
            System.out.println( e.getMessage() );
            fail( "Unespected error");
        }
    }

    @Test
    void saveAccountingBook__whenValidAccountingBook(){

        try{
            //when
            accountingBookService.saveAccountingBook( accountingBook );

            //when
        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }
}
