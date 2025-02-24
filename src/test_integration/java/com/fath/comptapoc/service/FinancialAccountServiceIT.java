package com.fath.comptapoc.service;

import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.services.impl.FinancialAccountServiceImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FinancialAccountServiceIT {

    @Autowired
    private FinancialAccountServiceImpl financialAccountService;

    @Test
    public void whenTestContextLoads_thenServiceTestIsNotNull(){
        assertThat( financialAccountService ).isNotNull();
    }

    @Test
    public void whenSaveFinancialAccount_thenIsValid(){
        try{
            //given
            String name = "test_name_1";
            int code = 4456;
            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setName( name );
            financialAccount.setCode( code );

            //when
            FinancialAccount financialAccountSaved = financialAccountService.saveFinancialAccount( financialAccount );

            //then
            assertEquals( financialAccountSaved.getName(), financialAccount.getName() );
            assertEquals( financialAccountSaved.getCode(), financialAccount.getCode() );
            assertThat( financialAccountSaved.getId() ).isNotNull();
        } catch ( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenSaveFinancialAccount_thenIsNoValid(){
        try {
            assertThrows(
                    ConstraintViolationException.class,
                    () -> financialAccountService.saveFinancialAccount(new FinancialAccount()),
                    "Expected ConstraintViolationException to throw, but it didn't"
            );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenGetFinancialAccountById_whenNotFound(){
        try{
            assertThrows(
                    ClassNotFoundException.class,
                    () -> financialAccountService.getFinancialAccountById( 999L ),
                    "Expected notFoundException to throw, but it didn't"
            );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenGetFinancialAccountByName_whenNotFound(){
        try{
            assertThrows(
                    ClassNotFoundException.class,
                    () -> financialAccountService.getFinancialAccountByName( "test_name" ),
                    "Expected notFoundException to throw, but it didn't"
            );
        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenGetFinancialAccountById_whenFound(){
        try{
            //given
            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setName( "test_name_2" );
            financialAccount.setCode( 38876 );

            FinancialAccount financialAccountSaved = financialAccountService.saveFinancialAccount( financialAccount );

            //when
            FinancialAccount financialAccountFromBdd = financialAccountService.getFinancialAccountById( financialAccountSaved.getId() );

            //then
            assertEquals( financialAccountFromBdd.getName(), financialAccountSaved.getName() );
            assertEquals( financialAccountFromBdd.getCode(), financialAccountSaved.getCode() );

        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenGetFinancialAccountByName_whenFound(){
        try{
            //given
            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setName( "test_name_3" );
            financialAccount.setCode( 38876 );

            FinancialAccount financialAccountSaved = financialAccountService.saveFinancialAccount( financialAccount );

            //when
            FinancialAccount financialAccountFromBdd = financialAccountService.getFinancialAccountByName( financialAccountSaved.getName() );

            //then
            assertEquals( financialAccountFromBdd.getName(), financialAccountSaved.getName() );
            assertEquals( financialAccountFromBdd.getCode(), financialAccountSaved.getCode() );

        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

    @Test
    public void whenDeleteFinancialAccount_thenIsOk(){
        try{
            //given
            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setName( "test_name_4" );
            financialAccount.setCode( 38876 );

            FinancialAccount financialAccountSaved = financialAccountService.saveFinancialAccount( financialAccount );

            //when
            financialAccountService.deleteFinancialAccount( financialAccountSaved.getId() );

            //then
            assertThrows(
                    ClassNotFoundException.class,
                    () -> financialAccountService.getFinancialAccountById( financialAccountSaved.getId() ),
                    "Expected notFoundException to throw, but it didn't"
            );

        } catch(Exception e){
            fail( e.getMessage() );
        }
    }

  /*  @Test //todo
    public void whenDeleteFinancialAccountWithOperation_thenIsNotPossible(){
        try{
            //given
            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setName( "test_name_4" );
            financialAccount.setCode( 38876 );


            FinancialAccount financialAccountSaved = financialAccountService.saveFinancialAccount( financialAccount );

            //when
            financialAccountService.deleteFinancialAccount( financialAccountSaved.getId() );

            //then
            assertThrows(
                    ClassNotFoundException.class,
                    () -> financialAccountService.getFinancialAccountById( financialAccountSaved.getId() ),
                    "Expected notFoundException to throw, but it didn't"
            );

        } catch(Exception e){
            fail( e.getMessage() );
        }
    }*/
}
