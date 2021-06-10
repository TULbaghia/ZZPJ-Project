package pl.lodz.p.it.zzpj.service.thesis.validator;

import pl.lodz.p.it.zzpj.service.thesis.validator.pattern.ThesisRegularExpression;

import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@Pattern(regexp = ThesisRegularExpression.DOI, message = "validation.doi.pattern")
public @interface Doi {
    String message() default "validation.doi";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
