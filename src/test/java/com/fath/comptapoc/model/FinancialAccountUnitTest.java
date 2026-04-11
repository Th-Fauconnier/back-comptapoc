package com.fath.comptapoc.model;

import com.fath.comptapoc.model.entities.FinancialAccount;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FinancialAccountUnitTest {

    private static Validator validator;

    private Set<ConstraintViolation<FinancialAccount>> violations;

    @BeforeAll
    public static void setup() {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory() ) {
            validator = factory.getValidator();
        } catch( Exception e ){
            fail( e.getMessage() );
        }
    }

    @Test
    public void financialAccount_whenIsValid(){
        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setId( 45L );
        financialAccount.setName( "Financial account name" );
        financialAccount.setCode( 4245 );

        violations = validator.validate( financialAccount );
        assertTrue( violations.isEmpty() );
    }

    @Test
    public void financialAccount_whenNameMissing_thenConstraintViolation() {
        FinancialAccount financialAccount = new FinancialAccount();
        financialAccount.setId( 45L );
        financialAccount.setCode( 4245 );

        try{
            violations = validator.validate( financialAccount );
            if( !violations.isEmpty() ){
                violations.forEach( violation -> assertThat( violation.getMessage() ).isEqualTo("Name for a financial account is mandatory") );
            } else fail( "ConstraintVilationException expected not throwing" );
        } catch( Exception e ){
            fail( e.getMessage() );
        }
    }

}
