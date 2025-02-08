package com.fath.comptapoc.config.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EntryBalanceValidator.class)
public @interface EntryBalance {

    String message() default "The balance of entry's operations isn't null";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
