package pl.lodz.p.it.zzpj.service.auth.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = RegularExpression.ROLE, message = "Role does not match size requirements")
public @interface Role {
    String message() default "Role is forbidden";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
