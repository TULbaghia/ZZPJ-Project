package pl.lodz.p.it.zzpj.utils.validation;

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
@Size(min = 2, max = 255, message = "{validation.phrase.size}")
@Pattern(regexp = RegularExpression.PHRASE, message = "{validation.phrase.pattern}")
public @interface Phrase {
    String message() default "{validation.phrase.pattern}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
