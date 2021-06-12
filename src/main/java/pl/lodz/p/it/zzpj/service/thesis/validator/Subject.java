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
@Pattern(regexp = ThesisRegularExpression.TOPIC, message = "validation.topic.pattern")
public @interface Subject {
    String message() default "validation.topic";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
