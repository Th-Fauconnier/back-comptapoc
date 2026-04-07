package com.fath.comptapoc.model.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OperationValidatorIT {

    @Autowired
    private static Validator validator;

    private static Operation testingOperation;

    private Set<ConstraintViolation <Operation > > violations;

    @BeforeAll
    public static void setup() {
        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setName( "Testing book");
        testingOperation = new Operation();
        testingOperation.setFinancialAccount( financialAccount );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenOperationAmount_haveBadNumberOfDigits(){
        testingOperation.setAmount(4.222d);

        violations = validator.validate( testingOperation );
        System.out.println("violations size : " + violations.size() );
        if( violations.size() == 1 ){
            violations.forEach( c -> assertThat( c.getMessage() ).isEqualTo( "The amount of this operation must have two digits." ) );
        } else {
            fail( "ConstraintVilationException expected not throwing" );
        }

    }

    @Test
    public void whenOperationAmount_haveGoodNumberOfDigits(){
        testingOperation.setAmount(4.20d);

        violations = validator.validate( testingOperation );

        assertTrue( violations.isEmpty() );

    }
}
