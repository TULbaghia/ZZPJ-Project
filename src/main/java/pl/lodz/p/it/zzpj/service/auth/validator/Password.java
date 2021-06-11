package pl.lodz.p.it.zzpj.service.auth.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Size(min=8, max=64, message = "Password does not match size requirements")
public @interface Password {
    String message() default "Password is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}