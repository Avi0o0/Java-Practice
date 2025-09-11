package com.akshat.practice.app.customannotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InnodeedEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InnodeedEmail {
    String message() default "Invalid Innodeed email. Use firstname.lastname@innodeed.com (lowercase only, optional numeric suffix)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
