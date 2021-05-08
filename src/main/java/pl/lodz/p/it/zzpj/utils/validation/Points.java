package pl.lodz.p.it.zzpj.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Min(value = 0, message = "{validation.card_attempt.points}")
@Digits(integer = 1, fraction = 5, message = "{validation.card_attempt.points}")
public @interface Points {
    String message() default "{validation.card_attempt.points}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
