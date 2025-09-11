package com.akshat.practice.app.customannotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InnodeedEmailValidator implements ConstraintValidator<InnodeedEmail, String> {

    private static final String INNODEED_REGEX = "^[a-z]+\\.[a-z]+\\d*@innodeed\\.com$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        
    	return email.matches(INNODEED_REGEX);
    }
}
