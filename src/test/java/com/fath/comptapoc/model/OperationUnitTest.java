package com.fath.comptapoc.model;

import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


public class OperationUnitTest {

    private static Validator validator;

    private Set<ConstraintViolation <Operation> > violations;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenAmountOperationHaveBadDigitsNumber_thenViolationsException() {
        Operation operation = new Operation();
        operation.setAmount( BigDecimal.valueOf( 4.005 ) );

        FinancialAccount financialAccountMocked = mock( FinancialAccount.class );
        operation.setFinancialAccount( financialAccountMocked );

        violations = validator.validate( operation );
        if( violations.size() > 0 ){
            violations.forEach( violation -> assertThat( violation.getMessage() ).isEqualTo("The amount of this operation must have two digits.") );
        } else fail( "ConstraintVilationException expected not throwing" );
    }

    @Test
    public void whenAmountOperationHaveGoodDigitsNumber_thenNoViolationsException() {
        //when
        Operation operation = new Operation();
        operation.setAmount( BigDecimal.valueOf( 4.06 ) );

        FinancialAccount financialAccountMocked = mock( FinancialAccount.class );
        operation.setFinancialAccount( financialAccountMocked );

        violations = validator.validate( operation );
        //then
        assertTrue( violations.isEmpty() );
    }

    @Test
    public void whenOperationHaventMandatoryFinancialAccount_thenViolationsException(){
        //when
        Operation operation = new Operation();
        operation.setAmount( BigDecimal.valueOf( 4.06 ) );
        //then
        try{
            violations = validator.validate( operation );
            if( violations.size() > 0 ){
                violations.forEach( violation -> assertThat( violation.getMessage() ).isEqualTo("Account is mandatory") );
            } else fail( "ConstraintVilationException expected not throwing" );
        } catch( Exception e ){
            fail( e.getMessage() );
        }

    }
}
