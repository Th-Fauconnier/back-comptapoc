package com.fath.comptapoc.config.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OperationAmountValidator.class)
public @interface OperationAmount {

    String message() default "The amount of this operation must have two digits.";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
