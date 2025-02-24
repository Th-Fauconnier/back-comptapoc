package com.fath.comptapoc.config.constraints;

import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EntryBalanceValidator implements ConstraintValidator<EntryBalance, List<Operation>> {

    @Override
    public boolean isValid(List<Operation> value, ConstraintValidatorContext context ){
        AtomicInteger balance = new AtomicInteger();
        value.forEach( operation -> balance.addAndGet( operation.getAmount() ) );
        return balance.intValue() == 0;
    }
}
