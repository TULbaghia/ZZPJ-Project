package pl.lodz.p.it.zzpj.service.thesis.validator;

import pl.lodz.p.it.zzpj.service.thesis.validator.pattern.ThesisRegularExpression;

import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = ThesisRegularExpression.WORD, message = "validation.word.pattern")
public @interface Word {
    String message() default "validation.word";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
