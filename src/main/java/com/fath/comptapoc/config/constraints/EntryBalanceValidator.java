package com.fath.comptapoc.config.constraints;

import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EntryBalanceValidator implements ConstraintValidator<EntryBalance, List<Operation>> {

    @Override
    public boolean isValid(List<Operation> value, ConstraintValidatorContext context ){
        double balance = 0d;
        for( Operation operation : value ){
            balance = balance + operation.getAmount();
        }
        return balance == 0;
    }
}
