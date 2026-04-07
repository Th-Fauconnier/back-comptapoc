package com.fath.comptapoc.config.constraints;

import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OperationAmountValidator implements ConstraintValidator<OperationAmount, Operation> {

    /**
     * Check is the amount of an Operation haven't more of two digits
     * @param operation
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(Operation operation, ConstraintValidatorContext context) {

        double amount = operation.getAmount();
        System.out.println("amount 1 : " + amount);
        amount *= 100;
        System.out.println("amount 2 : " + amount);
        return amount % 1 == 0;

    }
}
