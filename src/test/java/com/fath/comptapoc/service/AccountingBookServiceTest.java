package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.model.mappers.IAccountingBookMapper;
import com.fath.comptapoc.repository.AccountingBookRepository;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import org.apache.coyote.BadRequestException;
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
public class AccountingBookServiceTest {

    @InjectMocks
    AccountingBookServiceImpl accountingBookService;

    @Mock
    private AccountingBookRepository accountingBookRepository;

    @Mock
    private IAccountingBookMapper accountingBookMapper;

    @Mock
    private AccountingBook accountingBook;

    @Test
    void test_GetAccountingBookById_whenOptionalIsEmpty(){
        //when
        when( accountingBookRepository.findById( anyLong() ) )
                .thenReturn(Optional.empty() );
        //then
        try{
            Optional<AccountingBook> optionalAccountingBook = accountingBookService.getAccountingBookById( anyLong() );

            assertThat( optionalAccountingBook ).isEmpty();
        } catch(Exception e) {
            fail( e.getMessage() );
        }
    }

    @Test
    void test_GetAccountingBookById_whenOptionalIsNotEmpty() {
        //when
        when(accountingBookRepository.findById( anyLong() ) )
                .thenReturn(Optional.of( accountingBook ) );
        //then
        try {
            Optional<AccountingBook> optionalAccountingBook = accountingBookService.getAccountingBookById( anyLong() );

            assertThat( optionalAccountingBook ).isPresent();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void test_GetAllAccountingBook_whenEmptyList(){
        //TODO
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
    void test_GetAllAccountingBook_whenNotEmptyList(){
        //TODO
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
    void test_DeleteAccountingBook(){
        //TODO

        //then
        try{
            accountingBookService.deleteAccountingBook( anyLong() );

        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    void test_SaveAccountingBook__whenUnvalidAccountingBook(){
        //TODO

        //then
        try{
            accountingBookService.saveAccountingBook( accountingBook );
        } catch (Exception e ){
            System.out.println("rdrdrdr");
            assertThat( e.getMessage() ).isEqualTo( "Name is mandatory" );
          //  fail( e.getMessage() );
        }
    }

    @Test
    void test_SaveAccountingBook__whenValidAccountingBook(){
        //TODO

        //then
        try{

        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }
}
