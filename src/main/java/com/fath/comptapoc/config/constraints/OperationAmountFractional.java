package com.fath.comptapoc.config.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OperationAmountFractionalValidator.class)
public @interface OperationAmountFractional {

    String message() default "The amount of this operation must have two fractional digits.";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
