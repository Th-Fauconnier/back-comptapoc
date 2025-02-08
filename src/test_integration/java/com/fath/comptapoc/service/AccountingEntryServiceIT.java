package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.model.entities.Operation;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import com.fath.comptapoc.services.impl.AccountingEntryServiceImpl;
import com.fath.comptapoc.services.impl.FinancialAccountServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AccountingEntryServiceIT {
    @Autowired
    private AccountingEntryServiceImpl accountingEntryService;

    @Autowired
    private AccountingBookServiceImpl accountingBookService;

    @Autowired
    private FinancialAccountServiceImpl financialAccountService;

    @Test
    public void whenTestContextLoads_thenServiceTestIsNotNull(){
        assertThat( accountingEntryService ).isNotNull();
        assertThat( accountingBookService ).isNotNull();
        assertThat( financialAccountService ).isNotNull();
    }

    @Test
    public void whenSaveAccountingEntry_thenIsValid(){
        try{
            //given
            AccountingBook accountingBookSaved = createAndGiveBookFromBdd( "name_one" );
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setLabel( "test_label_1");

            accountingEntry.setDate( LocalDateTime.now() );
            accountingEntry.setAccountingBook( accountingBookSaved );

            FinancialAccount account1 = createAndGiveFinancialAccount( 456, "account_1");
            FinancialAccount account2 = createAndGiveFinancialAccount( 556, "account_2");
            addOperation( 450, account1, accountingEntry );
            addOperation( -450, account2, accountingEntry );
            //when
            AccountingEntry accountingEntrySaved = accountingEntryService.saveAccountingEntry( accountingEntry );

            //then
            assertEquals( accountingEntrySaved.getLabel(), accountingEntry.getLabel() );
            assertEquals( accountingEntrySaved.getDate(), accountingEntry.getDate() );
            assertEquals( accountingEntrySaved.getAccountingBook().getName(), accountingEntry.getAccountingBook().getName() );
            assertThat( accountingEntry.getId() ).isNotNull();
            assertThat( accountingEntry.getAccountingBook().getId() ).isNotNull();
        } catch (Exception e ) {
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenSaveAccountingEntry_thenIsNoValid(){
        assertThrows(
                ConstraintViolationException.class,
                () -> accountingEntryService.saveAccountingEntry( new AccountingEntry() ),
                "Expected ConstraintViolationException to throw, but it didn't"
        );
    }

    @Test
    public void whenSaveAccountingEntry_thenEntryBalanceIsNotValid(){
        //given
        AccountingBook accountingBookSaved = createAndGiveBookFromBdd( "name_one" );
        AccountingEntry accountingEntry = new AccountingEntry();
        accountingEntry.setLabel( "test_label_1");

        accountingEntry.setDate( LocalDateTime.now() );
        accountingEntry.setAccountingBook( accountingBookSaved );

        FinancialAccount account1 = createAndGiveFinancialAccount( 456, "account_1");
        FinancialAccount account2 = createAndGiveFinancialAccount( 556, "account_2");
        addOperation( 451, account1, accountingEntry );
        addOperation( -450, account2, accountingEntry );

        //when //then
        assertThrows(
                ConstraintViolationException.class,
                () -> accountingEntryService.saveAccountingEntry( new AccountingEntry() ),
                "Expected ConstraintViolationException to throw, but it didn't"
        );
    }

    @Test
    public void whenGetAccountingEntryById_whenNotFound(){
        try{
            assertThrows(
              ClassNotFoundException.class,
                    () -> accountingEntryService.getAccountingEntryById( 999L ),
                    "Expected notFoundException to throw, but it didn't"
            );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenGetAccountingEntryById_whenFound(){
        try{
            //given
            AccountingBook accountingBook = createAndGiveBookFromBdd( "test_book_1" );
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setLabel( "test_label_1" );
            accountingEntry.setDate( LocalDateTime.now() );
            accountingEntry.setAccountingBook( accountingBook );

            FinancialAccount account1 = createAndGiveFinancialAccount( 456, "account_1");
            FinancialAccount account2 = createAndGiveFinancialAccount( 556, "account_2");
            addOperation( 450, account1, accountingEntry );
            addOperation( -450, account2, accountingEntry );

            AccountingEntry accountingEntrySaved = accountingEntryService.saveAccountingEntry( accountingEntry );

            //when
            AccountingEntry accountingEntryFromDB = accountingEntryService.getAccountingEntryById( accountingEntrySaved.getId() );

            //then
            assertEquals( accountingEntryFromDB.getLabel(), accountingEntrySaved.getLabel() );
            assertEquals( accountingEntryFromDB.getDate(), accountingEntrySaved.getDate() );
            assertEquals( accountingEntryFromDB.getAccountingBook().getName(), accountingEntrySaved.getAccountingBook().getName() );

        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenGetAllEntries(){
        try{
            //given
            AccountingBook accountingBook = createAndGiveBookFromBdd("test_book_3");
            Set<AccountingEntry> accountingEntries = Set.of(
                    giveNewAccountingEntry( "test_label_2", accountingBook),
                    giveNewAccountingEntry( "test_label_3", accountingBook),
                    giveNewAccountingEntry( "test_label_4", accountingBook)
            );
            accountingEntries.forEach( ae -> {
                FinancialAccount account1 = createAndGiveFinancialAccount( 456, "account_1");
                FinancialAccount account2 = createAndGiveFinancialAccount( 556, "account_2");
                addOperation( 450, account1, ae );
                addOperation( -450, account2, ae );

                accountingEntryService.saveAccountingEntry( ae );
            } );

            //when
            List<AccountingEntry> accountingEntriesFromDB = accountingEntryService.getAll();

            //then
            assertEquals( accountingEntriesFromDB.size(), accountingEntries.size() );
            assertTrue( accountingEntriesFromDB.containsAll( accountingEntries ) );

        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenDeleteAccountingEntry_thenIsOk(){
        try{
            //give
            AccountingEntry accountingEntry = giveNewAccountingEntry(
                    "test_label_5",
                    createAndGiveBookFromBdd( " test_book_4")
            );
            FinancialAccount account1 = createAndGiveFinancialAccount( 456, "account_1");
            FinancialAccount account2 = createAndGiveFinancialAccount( 556, "account_2");
            addOperation( 450, account1, accountingEntry );
            addOperation( -450, account2, accountingEntry );

            AccountingEntry accountingEntrySaved = accountingEntryService.saveAccountingEntry( accountingEntry );

            //when
            accountingEntryService.deleteAccountingEntry( accountingEntrySaved.getId() );

            //then
            assertThrows(
                    ClassNotFoundException.class,
                    () -> accountingEntryService.getAccountingEntryById( accountingEntrySaved.getId() ),
                    "Expected notFoundException to throw, but it didn't"
            );

        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    private AccountingEntry giveNewAccountingEntry( String label, AccountingBook book ){
        AccountingEntry accountingEntry = new AccountingEntry();
        accountingEntry.setLabel( label );
        accountingEntry.setDate( LocalDateTime.now() );
        accountingEntry.setAccountingBook( book );
        return accountingEntry;
    }

    private AccountingBook createAndGiveBookFromBdd( String bookName ) {
        AccountingBook book = new AccountingBook();
        book.setName( bookName );
        return accountingBookService.saveAccountingBook( book );
    }

    private void addOperation(int amount, FinancialAccount account, AccountingEntry entry ){
        Operation operation = new Operation();
        operation.setAmount( amount );
        operation.setFinancialAccount( account );

        operation.setAccountingEntry( entry );
        entry.getOperations().add( operation );
    }

    private FinancialAccount createAndGiveFinancialAccount( int code, String name ){
        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setCode( code );
        financialAccount.setName( name );
        return financialAccountService.saveFinancialAccount( financialAccount );
    }
}
