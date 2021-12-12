package com.example.balance.service.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { RequestValidator.class })
public @interface IsValidRequest {
    String message() default "Request is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
