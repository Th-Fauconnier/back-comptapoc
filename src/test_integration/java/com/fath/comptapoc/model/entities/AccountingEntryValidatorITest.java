package com.fath.comptapoc.model.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AccountingEntryValidatorITest {

    @Autowired
    private static Validator validator;

    private Set<ConstraintViolation < AccountingEntry > > violations;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void accountingEntryValidator_whenIsValid(){
        try{
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setLabel( "testLabel" );
            accountingEntry.setDate( LocalDateTime.now() );

            violations = validator.validate( accountingEntry );

            assertTrue( violations.isEmpty() );
           /* if( violations.isEmpty() ) {
                assertThat( accountingEntry.getLabel() ).isEqualTo( "testLabel" );
            } else {
                fail( "unexpected constraintViolationException has been throwed" );
            }*/
        } catch (Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void accountingEntryLabelValidator_whenLabelIsNull(){
        try{
            AccountingEntry accountingEntry = new AccountingEntry();
            accountingEntry.setDate( LocalDateTime.now() );
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

    //todo test relation AccountingBook

    //todo test Operations

}
