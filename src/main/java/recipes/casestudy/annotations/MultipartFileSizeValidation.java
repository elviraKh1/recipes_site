package recipes.casestudy.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MultipartFileSizeValidator.class)
public @interface MultipartFileSizeValidation {

    String message() default "File too Large.";

    int fileSize() default 1048576;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}