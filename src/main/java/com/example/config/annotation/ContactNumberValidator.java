package com.example.config.annotation;

import javax.enterprise.context.Dependent;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

@Dependent
public class ContactNumberValidator implements ConstraintValidator<ContactNumber, String> {

    private final String PHONE_NUMBER_REQEX = "^(070|080|090)-\\d{4}-\\d{4}$";
    private final String TEL_NUMBER_REQEX = "^0\\d{2,3}-\\d{1,4}-\\d{4}$";

    @Override
    public void initialize(ContactNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return contactNumberMatch.test(value);
    }

    private final Predicate<String> contactNumberMatch = (contact) -> {
        return contact.matches(PHONE_NUMBER_REQEX) || contact.matches(TEL_NUMBER_REQEX);
    };
}
