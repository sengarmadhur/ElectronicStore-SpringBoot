package com.ms.electronic.store.ElectronicStore.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    //error message
    String message() default "Invalid Image Name";

    //represents group of constraints
    Class<?>[] groups() default {};

    //Additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
