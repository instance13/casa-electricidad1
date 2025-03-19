package com.egg.casa_electricidad.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.egg.casa_electricidad.validadores.RoleValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = RoleValidator.class) // which class contains the validation logic -> it uses reflection ! :D
@Target({ ElementType.FIELD }) // restricts where this annotation can be applied. in this case, it can be applied exclusively in fields.
@Retention(RetentionPolicy.RUNTIME) // RetentionPolicy.RUNTIME means it will be available at runtime through reflection (necessary for validation to work).

// the @interface keyword is used to define an annotation.
public @interface ValidRole {
  String message() default "Rol inválido";

  // allows for grouping constraints together for validation at different stages
  Class<?>[] groups() default {};

  // rarely used directly but required in custom constraint annotations
  Class<? extends Payload>[] payload() default {};
}
