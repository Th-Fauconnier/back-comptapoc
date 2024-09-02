package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.services.impl.AccountingBookServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AccountingBookServiceIT {
    @Autowired
    private AccountingBookServiceImpl accountingService;

    @Value("${example.firstProperty}") private String firstProperty;
    @Value("${example.secondProperty}") private String secondProperty;

    @Test
    public void whenTestContextLoads_thenServiceTestISNotNull() {
        assertThat(accountingService).isNotNull();
    }

    @Test
    public void shouldProfiledProperty_overridePropertyValues() {

        assertEquals("profile", firstProperty);
        assertEquals("defaultSecond", secondProperty);
    }

    @Test
    public void saveAccountingBook_thenIsNotValid(){
        assertThrows(
                ConstraintViolationException.class,
                () -> accountingService.saveAccountingBook( new AccountingBook() ),
                "Expected ConstraintViolationException to throw, but it didn't"
        );

    }

    @Test
    public void saveAccountingBook_thenIsValid(){
        //when
        AccountingBook accountingBook = new AccountingBook();
        accountingBook.setName( "testName_01" );

        AccountingBook accountingBookSaved = accountingService.saveAccountingBook( accountingBook );

        assertEquals( accountingBook.getName(), accountingBookSaved.getName() );
        assertThat( accountingBook.getId() ).isNotNull();

    }

    @Test
    public void getAccountingBookById_whenNotFound(){
        try{
            assertThrows(
                    ClassNotFoundException.class,
                    () -> accountingService.getAccountingBookById( 999L ),
                    "Expected notFoundException to throw, but it didn't"
            );
        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void getAccountingBookById_whenFound(){
        try{
            AccountingBook accountingBook = new AccountingBook();
            accountingBook.setName( "testName_02" );
            AccountingBook accountingBookSaved = accountingService.saveAccountingBook( accountingBook );

           AccountingBook accountingBookFromDB = accountingService.getAccountingBookById( accountingBookSaved.getId() );
           assertEquals( accountingBookFromDB.getName(), "testName_02"  );
           assertEquals( accountingBookFromDB.getId(), accountingBook.getId() );

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void getAllAccountingBooks(){
        try{

            Set<AccountingBook> accountingBooks = new HashSet<>();
            AccountingBook ac1 = new AccountingBook();
            ac1.setName("test_03");
            AccountingBook ac2 = new AccountingBook();
            ac2.setName("test_04");
            AccountingBook ac3 = new AccountingBook();
            ac3.setName("test_05");
            accountingBooks.add( ac1);
            accountingBooks.add( ac2);
            accountingBooks.add( ac3);

            accountingBooks.forEach( ac -> accountingService.saveAccountingBook( ac ));

            Set<AccountingBook> accountingBooksFromBdd = accountingService.getAllAccountingBooks();

            assertEquals( accountingBooksFromBdd.size(), 3);
            assertTrue( accountingBooksFromBdd.containsAll( accountingBooks ) ) ;

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void deleteAccountingBook(){
        try {
            AccountingBook accountingBook = new AccountingBook();
            accountingBook.setName( "testName" );
            AccountingBook accountingBookSaved = accountingService.saveAccountingBook( accountingBook );

            accountingService.deleteAccountingBook( accountingBookSaved.getId() );

            assertThrows(
                    ClassNotFoundException.class,
                    () -> accountingService.getAccountingBookById( accountingBookSaved.getId() ),
                    "Expected notFoundException to throw, but it didn't"
            );

        } catch(Exception e ){
            fail( e.getMessage() );
        }
    }
}
