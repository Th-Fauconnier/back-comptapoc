package com.fath.comptapoc.config.constraints;

import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.List;

public class EntryBalanceValidator implements ConstraintValidator<EntryBalance, List<Operation>> {

    @Override
    public boolean isValid(List<Operation> value, ConstraintValidatorContext context ){
        BigDecimal balance = BigDecimal.valueOf( 0 );
      //  double balance = 0d;
        for( Operation operation : value ){
            balance = balance.add( operation.getAmount() );
        }

        //Remove trailing zeros
        String plainString = balance.toPlainString();
        // Step 3: Split into integer and fractional parts
        String[] parts = plainString.split("\\.", 2);
        String integerPart = parts[0];
        String fractionalPart = parts.length > 1 ? parts[1] : "";

        // Step 5: Process fractional part: remove trailing zeros
        fractionalPart = fractionalPart.replaceAll("0+$", ""); // Remove trailing zeros
        String significantDigits = integerPart + fractionalPart;

        return significantDigits.equals( "0" );
    }

}
