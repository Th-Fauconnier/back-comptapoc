package com.fath.comptapoc.config.constraints;

import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OperationAmountValidator implements ConstraintValidator<OperationAmount, Operation> {

    /**
     * Check if the decimal part of amount for an Operation haven't more of two digits
     * @param operation
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(Operation operation, ConstraintValidatorContext context) {

        String amount = operation.getAmount().toPlainString();

        // Split into integer and fractional parts
        String[] parts = amount.split("\\.", 2);
        // If the partional parts exist, retrieve it, or just get a blank string
        String fractionalPart = parts.length > 1 ? parts[1] : "";

        // Process fractional part: remove trailing zeros
        fractionalPart = fractionalPart.replaceAll("0+$", ""); // Remove trailing zeros

        // return if the partional part have 2 digits or less
        return fractionalPart.length() <= 2;

    }
}
