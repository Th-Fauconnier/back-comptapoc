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
        try( ValidatorFactory factory = Validation.buildDefaultValidatorFactory() ){
            validator = factory.getValidator();
        } catch( Exception e ){
            fail( e.getMessage() );
        }

    }


    @Test
    public void whenOperationHaventMandatoryFinancialAccount_thenViolationsException(){
        //when
        Operation operation = new Operation();
        operation.setAmount( BigDecimal.valueOf( 4.06 ) );
        //then
        try{
            violations = validator.validate( operation );
            if( !violations.isEmpty() ){
                violations.forEach( violation -> assertThat( violation.getMessage() ).isEqualTo("Financial account for operation is mandatory") );
            } else fail( "ConstraintVilationException expected not throwing" );
        } catch( Exception e ){
            fail( e.getMessage() );
        }

    }

    @Test
    public void whenOperationIsFullyValid_thenNoViolationException(){
        //when
        Operation operation = new Operation();
        operation.setAmount( BigDecimal.valueOf( 40.6 ) );
        FinancialAccount mockedFinancialAccount = mock( FinancialAccount.class );
        operation.setFinancialAccount( mockedFinancialAccount );

        //then
        violations = validator.validate( operation );
        assertThat( violations.isEmpty() );
    }
}
