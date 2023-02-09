package com.example.config.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContactNumberValidator.class)
public @interface ContactNumber {
    String message() default "Error in the format of the phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
