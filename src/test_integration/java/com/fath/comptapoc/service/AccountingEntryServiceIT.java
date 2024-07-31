package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.services.impl.AccountingEntryServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AccountingEntryServiceIT {

    @Autowired
    private AccountingEntryServiceImpl accountingEntryService;

    @Test
    public void whenTestContextLoads_thenServiceTestIsNotNull(){
        assertThat( accountingEntryService ).isNotNull();
    }

    @Test
    public void saveAccountingEntry_thenIsNotValid(){
        assertThrows(
          ConstraintViolationException.class,
                () -> accountingEntryService.saveAccountingEntry( new AccountingEntry() ),
                "Excpected ConstraintViolationException to throw, but didn't"
        );
    }

    @Test
    public void saveAccountingEntry_thenIsValid(){
        //given
        AccountingEntry accountingEntry = giveNewAccountingEntry(
                "test_label_1",
                giveNewAccountingBook( "test_book_1")
        );

        //when
        try{
            AccountingEntry accountingEntrySaved = accountingEntryService.saveAccountingEntry( accountingEntry );
            //then
            assertThat( accountingEntrySaved.getId() ).isNotNull();
            assertEquals( accountingEntry.getLabel(), accountingEntrySaved.getLabel() );
            assertEquals( accountingEntry.getDate(), accountingEntrySaved.getDate() );
            assertEquals( accountingEntry.getAccountingBook().getName(), accountingEntrySaved.getAccountingBook().getName() );
        } catch (Exception e ) {
            fail( e.getMessage() );
        }
    }

    @Test
    public void getAccountingEntryById_thenNotFound(){
        try{
            assertThrows(
                    ClassNotFoundException.class,
                    () -> accountingEntryService.getAccountingEntryById( 999L ),
                    "Expected notFoundException to thrown, but didn't"
            );
        }catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void getAccountingEntryById_thenFoundIt(){
        try{
            //given
            AccountingEntry accountingEntry = giveNewAccountingEntry(
                    "test_label_2",
                    giveNewAccountingBook( "test_book_2")
            );
            AccountingEntry accountingEntrySaved = accountingEntryService.saveAccountingEntry( accountingEntry );

            //when
            AccountingEntry accountingEntryFromDB = accountingEntryService.getAccountingEntryById( accountingEntrySaved.getId() );

            //then
            assertEquals( accountingEntryFromDB.getLabel(), accountingEntrySaved.getLabel() );
            assertEquals( accountingEntryFromDB.getDate(), accountingEntrySaved.getDate() );
            assertEquals( accountingEntryFromDB.getAccountingBook().getId(), accountingEntrySaved.getAccountingBook().getId() );

        } catch(Exception e ) {
            fail (e.getMessage() );
        }
    }

    @Test void getAllAccountingEntry(){
        try{
            //given
            Set<AccountingEntry> accountingEntries = new HashSet<>();
            AccountingBook ab1 = giveNewAccountingBook("test_book_3");
            AccountingBook ab2 = giveNewAccountingBook("test_book_4");
            AccountingEntry ae1 = giveNewAccountingEntry("test_label_4", ab1);
            AccountingEntry ae2 = giveNewAccountingEntry("test_label_5", ab1);
            AccountingEntry ae3 = giveNewAccountingEntry("test_label_6", ab2);
            accountingEntries.add(ae1);
            accountingEntries.add(ae2);
            accountingEntries.add(ae3);

            accountingEntries.forEach( ae -> accountingEntryService.saveAccountingEntry( ae ) );

            //when
            Set<AccountingEntry> accountingEntriesFromDB = accountingEntryService.getAll();

            //then
            assertEquals( accountingEntriesFromDB.size(), 3);
            assertTrue( accountingEntriesFromDB.containsAll( accountingEntries ) );

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void deleteAccountingEntry(){
        try{
            //given
            AccountingBook ac = giveNewAccountingBook("test_book_6" );
            AccountingEntry ae = giveNewAccountingEntry( "test_label_8", ac );
            AccountingEntry accountingEntryFromDB = accountingEntryService.saveAccountingEntry( ae );

            //when
            accountingEntryService.deleteAccountingEntry( accountingEntryFromDB.getId() );

            //then
            assertThrows(
                    ClassNotFoundException.class,
                    () -> accountingEntryService.getAccountingEntryById( accountingEntryFromDB.getId() ),
                    "Expected notFoundException to throw, but it didn't"
            );

         } catch(Exception e) {
            fail( e.getMessage() );
        }
    }


    private AccountingEntry giveNewAccountingEntry( String label, AccountingBook accountingBook ){
        AccountingEntry accountingEntry = new AccountingEntry();
        accountingEntry.setLabel( label );
        accountingEntry.setDate( LocalDateTime.now() );
        accountingEntry.setAccountingBook( accountingBook );
        return accountingEntry;
    }

    private AccountingBook giveNewAccountingBook(String name){
        AccountingBook accountingBook = new AccountingBook();
        accountingBook.setName( name );
        return accountingBook;
    }
}
