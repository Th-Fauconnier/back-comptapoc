package com.fath.comptapoc.model.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountingBookValidatorITest {

    @Autowired
    private static Validator validator;

    private static AccountingBook accountingBook;
    private Set< ConstraintViolation< AccountingBook > > violations;

    @BeforeAll
    public static void setUp() {
        accountingBook = new AccountingBook();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void accountingBookNameValidator_whenNameIsNotNull(){

        try{
            accountingBook.setName( "testName" );

            violations = validator.validate( accountingBook );

            if( violations.isEmpty() ){
                assertThat( accountingBook.getName() ).isEqualTo(  "testName");
            } else {
                fail( "unexpected constraintViolationException has been throwed" );
            }

        } catch (Exception e){
            fail( e.getMessage() );
        }

    }

    @Test
    public void accountingBookNameValidator_whenNameIsNull(){

        try{
            violations = validator.validate( accountingBook );

            if( violations.size() == 1 ){
                violations.forEach( c -> assertThat( c.getMessage() ).isEqualTo( "Name for accountingBook is mandatory") );
            } else {
                fail( "ConstraintViolationException expected not throwing" );
            }

        } catch (Exception e){
            fail( e.getMessage() );
        }

    }

}
