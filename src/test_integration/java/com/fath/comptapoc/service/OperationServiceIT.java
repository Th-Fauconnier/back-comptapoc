package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.model.entities.Operation;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import com.fath.comptapoc.services.impl.AccountingEntryServiceImpl;
import com.fath.comptapoc.services.impl.FinancialAccountServiceImpl;
import com.fath.comptapoc.services.impl.OperationServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OperationServiceIT {

    @Autowired
    private OperationServiceImpl operationService;

    @Autowired
    private FinancialAccountServiceImpl financialAccountService;

    @Autowired
    private AccountingEntryServiceImpl accountingEntryService;

    @Autowired
    private AccountingBookServiceImpl accountingBookService;

    @Test
    public void whenTestContextLoads_thenServiceTestIsNotNull(){
        assertThat( operationService ).isNotNull();
        assertThat( financialAccountService ).isNotNull();
        assertThat( accountingEntryService ).isNotNull();
        assertThat( accountingBookService ).isNotNull();
    }

    @Test
    public void whenSaveOperation_thenIsCreated(){
        //given
        int amount = 565;
        String entryLabel = "entry_label";
        String accountBookName = "account_book_name_1";

        Operation operation = new Operation();
        operation.setAmount( amount );

        FinancialAccount financialAccount = createAndGiveFinancialAccountFromBdd( "account_name_1", 4552);
        operation.setFinancialAccount( financialAccount );
        operation.setAccountingEntry(
                createAndGiveAccountEntryFromBdd(
                        entryLabel,
                        createAndGiveBookFromBdd( accountBookName )
                )
        );

        //when
        Operation operationCreated = operationService.saveOperation( operation );

        //then
        assertThat( operationCreated.getId() ).isNotNull();
        assertEquals( operationCreated.getAmount(), operation.getAmount() );
        assertEquals( operationCreated.getAccountingEntry().getLabel(), operation.getAccountingEntry().getLabel() );
        assertEquals( operationCreated.getFinancialAccount().getName(), operation.getFinancialAccount().getName() );
        assertEquals( operationCreated.getFinancialAccount().getCode(), operation.getFinancialAccount().getCode() );
    }

    @Test
    public void whenSaveOperation_thenIsNoValid(){
        assertThrows(
                ConstraintViolationException.class,
                () -> operationService.saveOperation( new Operation() ),
                "Expected ConstraintViolationException to throw, but it didn't"
        );
    }

    @Test
    public void whenGetOperationById_whenNotFound(){
        try{
            assertThrows(
                    ClassNotFoundException.class,
                    () -> operationService.getOperationById( 999L ),
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
            FinancialAccount financialAccount = createAndGiveFinancialAccountFromBdd( "account_name_3", 444);
            AccountingBook accountingBook = createAndGiveBookFromBdd( "book_name_3");
            AccountingEntry accountingEntry = createAndGiveAccountEntryFromBdd( "entry_label_3", accountingBook );
            Operation operation = new Operation();
            operation.setFinancialAccount( financialAccount );
            operation.setAccountingEntry( accountingEntry );
            operation.setAmount( 45 );

            Operation operationSaved = operationService.saveOperation( operation );

            //when
            Operation operationFromBdd = operationService.getOperationById( operationSaved.getId() );

            //then
            assertEquals( operationFromBdd.getAmount(), operationSaved.getAmount() );
            assertEquals( operationFromBdd.getAccountingEntry().getLabel(), operationSaved.getAccountingEntry().getLabel() );
            assertEquals( operationFromBdd.getFinancialAccount().getName(), operationSaved.getFinancialAccount().getName() );
            assertEquals( operationFromBdd.getFinancialAccount().getCode(), operationSaved.getFinancialAccount().getCode() );

        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    private AccountingBook createAndGiveBookFromBdd( String bookName ) {
        AccountingBook book = new AccountingBook();
        book.setName( bookName );
        return accountingBookService.saveAccountingBook( book );
    }

    private FinancialAccount createAndGiveFinancialAccountFromBdd( String accountName, int accountCode ){
        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setName( accountName );
        financialAccount.setCode( accountCode );
        return financialAccountService.saveFinancialAccount( financialAccount );
    }

    private AccountingEntry createAndGiveAccountEntryFromBdd( String label, AccountingBook book ){
        AccountingEntry accountingEntry = new AccountingEntry();
        accountingEntry.setLabel( label );
        accountingEntry.setDate( LocalDateTime.now() );
        accountingEntry.setAccountingBook( book );
        return accountingEntryService.saveAccountingEntry( accountingEntry );
    }
}
