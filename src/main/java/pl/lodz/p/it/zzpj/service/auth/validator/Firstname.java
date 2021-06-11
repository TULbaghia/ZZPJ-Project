package pl.lodz.p.it.zzpj.service.auth.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Size(min = 3, max = 31, message = "Firstname does not match size requirements")
@Pattern(regexp = RegularExpression.FIRSTNAME, message = "Firstname does not match pattern requirements")
public @interface Firstname {
    String message() default "Firstname is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
