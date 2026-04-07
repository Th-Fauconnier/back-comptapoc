package com.fath.comptapoc.model.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AccountingEntryValidatorITest {

    @Autowired
    private static Validator validator;

    private static AccountingBook accountingBook;

    private Set<ConstraintViolation < AccountingEntry > > violations;

    @BeforeAll
    public static void setup() {
        accountingBook = new AccountingBook();
        accountingBook.setName( "test_book" );
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void accountingEntryValidator_whenIsValid(){
        try{
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setLabel( "testLabel" );
            accountingEntry.setDate( LocalDateTime.now() );
            accountingEntry.setAccountingBook( accountingBook );

            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setCode( 444 );
            financialAccount.setName( "account_name" );

            Operation operation1 = new Operation();
            operation1.setFinancialAccount( financialAccount );
            operation1.setAmount( 450.20d );
            operation1.setAccountingEntry( accountingEntry );

            Operation operation2 = new Operation();
            operation2.setFinancialAccount( financialAccount );
            operation2.setAmount( -450.20d );
            operation2.setAccountingEntry( accountingEntry );

            accountingEntry.getOperations().add( operation1 );
            accountingEntry.getOperations().add( operation2 );

            System.out.println( "amount for op1 : " + operation1.getAmount() );
            System.out.println( "amount for op2 : " + operation2.getAmount() );

            violations = validator.validate( accountingEntry );

            if( !violations.isEmpty() ){
                violations.forEach( violation -> System.out.println( violation.getMessage() ));
            }
            assertTrue( violations.isEmpty() );
        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void accountingEntryValidator_whenBalanceIsNotValid(){
        try{
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setLabel( "testLabel" );
            accountingEntry.setDate( LocalDateTime.now() );
            accountingEntry.setAccountingBook( accountingBook );

            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setCode( 444 );
            financialAccount.setName( "account_name" );

            Operation operation1 = new Operation();
            operation1.setFinancialAccount( financialAccount );
            operation1.setAmount( 451d );
            operation1.setAccountingEntry( accountingEntry );

            Operation operation2 = new Operation();
            operation2.setFinancialAccount( financialAccount );
            operation2.setAmount( -450d );
            operation2.setAccountingEntry( accountingEntry );

            accountingEntry.getOperations().add( operation1 );
            accountingEntry.getOperations().add( operation2 );

            violations = validator.validate( accountingEntry );

            if( violations.size() == 1 ){
                violations.forEach( c -> assertThat( c.getMessage() ).isEqualTo( "The balance of entry's operations isn't null" ) );
            } else {
                fail( "ConstraintVilationException expected not throwing" );
            }
        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void accountingEntryLabelValidator_whenLabelIsNull(){
        try{
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setDate( LocalDateTime.now() );
            accountingEntry.setAccountingBook( accountingBook );

            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setCode( 444 );
            financialAccount.setName( "account_name" );

            Operation operation1 = new Operation();
            operation1.setFinancialAccount( financialAccount );
            operation1.setAmount( 450 );
            operation1.setAccountingEntry( accountingEntry );

            Operation operation2 = new Operation();
            operation2.setFinancialAccount( financialAccount );
            operation2.setAmount( -450 );
            operation2.setAccountingEntry( accountingEntry );

            accountingEntry.getOperations().add( operation1 );
            accountingEntry.getOperations().add( operation2 );

            violations = validator.validate( accountingEntry );

            if( violations.size() == 1 ){
                violations.forEach( c -> assertThat( c.getMessage() ).isEqualTo( "Label is mandatory" ) );
            } else {
                fail( "ConstraintVilationException expected not throwing" );
            }
        } catch (Exception e) {
            fail( e.getMessage() );
        }
    }

    @Test
    public void accountingEntryDateValidator_whenDateIsNull(){
        try {
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setLabel( "testLabel" );
            accountingEntry.setAccountingBook( accountingBook );

            FinancialAccount financialAccount = new FinancialAccount();
            financialAccount.setCode( 444 );
            financialAccount.setName( "account_name" );

            Operation operation1 = new Operation();
            operation1.setFinancialAccount( financialAccount );
            operation1.setAmount( 450 );
            operation1.setAccountingEntry( accountingEntry );

            Operation operation2 = new Operation();
            operation2.setFinancialAccount( financialAccount );
            operation2.setAmount( -450 );
            operation2.setAccountingEntry( accountingEntry );

            accountingEntry.getOperations().add( operation1 );
            accountingEntry.getOperations().add( operation2 );
            violations = validator.validate( accountingEntry );

            if( violations.size() == 1 ){
                violations.forEach( c -> assertThat( c.getMessage() ).isEqualTo( "Date is mandatory" ) );
            } else {
                fail( "ConstraintVilationException expected not throwing" );
            }
        } catch (Exception e) {
            fail( e.getMessage() );
        }
    }

    @Test
    public void accountingEntryAccountingBookRelation_whenAccountingBookIsNull(){
        AccountingEntry accountingEntry = new AccountingEntry();
        accountingEntry.setLabel( "testLabel" );
        accountingEntry.setDate( LocalDateTime.now() );

        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setCode( 444 );
        financialAccount.setName( "account_name" );

        Operation operation1 = new Operation();
        operation1.setFinancialAccount( financialAccount );
        operation1.setAmount( 450 );
        operation1.setAccountingEntry( accountingEntry );

        Operation operation2 = new Operation();
        operation2.setFinancialAccount( financialAccount );
        operation2.setAmount( -450 );
        operation2.setAccountingEntry( accountingEntry );

        accountingEntry.getOperations().add( operation1 );
        accountingEntry.getOperations().add( operation2 );

        violations = validator.validate( accountingEntry );

        if( violations.size() == 1 ){
            violations.forEach( c -> assertThat( c.getMessage() ).isEqualTo( "AccountingBook is mandatory" ) );
        } else {
            fail( "ConstraintVilationException expected not throwing" );
        }
    }

    @Test
    public void whenValidationEntry_whenEntryHaventOperations(){
      AccountingEntry accountingEntry = new AccountingEntry();
      accountingEntry.setLabel( "entry_label");
      accountingEntry.setDate( LocalDateTime.now() );
      accountingEntry.setAccountingBook( accountingBook );

        violations = validator.validate( accountingEntry );

        violations.forEach( v -> {
            System.out.println( v.getMessage() );
        });

        if( violations.size() == 1 ){
            violations.forEach( c -> assertThat( c.getMessage() ).isEqualTo( "Operation list must have two or more operation" ) );
        } else {
            fail( "ConstraintVilationException expected not throwing" );
        }

      accountingEntry.setOperations( new ArrayList<>() );

      violations = validator.validate( accountingEntry );

        if( violations.size() == 1 ){
            violations.forEach( c -> assertThat( c.getMessage() ).isEqualTo( "Operation list must have two or more operation" ) );
        } else {
            fail( "ConstraintVilationException expected not throwing" );
        }
    }

}
