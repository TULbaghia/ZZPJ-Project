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
@Min(value = 0, message = "{validation.card_deck_attempt.score}")
@Digits(integer = 3, fraction = 2, message = "{validation.card_deck_attempt.score}")
public @interface Score {
    String message() default "{validation.card_deck_attempt.score}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
