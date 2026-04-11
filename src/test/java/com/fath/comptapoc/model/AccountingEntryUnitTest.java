package com.fath.comptapoc.model;

import com.fath.comptapoc.model.entities.AccountingBook;
import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.model.entities.FinancialAccount;
import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class AccountingEntryUnitTest {

    private static Validator validator;

    private Set<ConstraintViolation <AccountingEntry > > violations;

    @BeforeAll
    public static void setup() {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory() ) {
            validator = factory.getValidator();
        } catch( Exception e ){
            fail( e.getMessage() );
        }
    }
    @Test
    public void whenAccountingEntryIsValid_ThenNoViolationException(){
        //when
        AccountingEntry accountingEntryUnderTest = new AccountingEntry();
        AccountingBook accountingBookMock = mock( AccountingBook.class );
        accountingEntryUnderTest.setAccountingBook( accountingBookMock );
        accountingEntryUnderTest.setDate( LocalDateTime.now() );
        accountingEntryUnderTest.setId( 46L );
        accountingEntryUnderTest.setLabel( "label test");

        List<Operation> operationList = new ArrayList<>();
        operationList.add( getOperationMock( BigDecimal.valueOf( 402.31 ), getFinancialAccountMock("fa_1", 458) ) );
        operationList.add( getOperationMock( BigDecimal.valueOf( -402.31 ), getFinancialAccountMock("fa_2", 458) ) );

        accountingEntryUnderTest.setOperations( operationList );

        violations = validator.validate( accountingEntryUnderTest );
        if( !violations.isEmpty() ){
            violations.forEach( violation -> {
                System.out.println( violation.getMessage() );
            });
        }
        assertTrue( violations.isEmpty() );
    }

    private Operation getOperationMock(BigDecimal amount, FinancialAccount financialAccount){
        Operation operationMock = mock( Operation.class );
        operationMock.setFinancialAccount( financialAccount );
        operationMock.setAmount( amount );
        when( operationMock.getAmount() ).thenReturn( amount );
        when( operationMock.getFinancialAccount() ).thenReturn( financialAccount );
        return operationMock;
    }

    private FinancialAccount getFinancialAccountMock(String name, int code){
        FinancialAccount financialAccountMock = mock(FinancialAccount.class );
        when( financialAccountMock.getName() ).thenReturn( name );
        when( financialAccountMock.getCode() ).thenReturn( code );
        return financialAccountMock;
    }
}
