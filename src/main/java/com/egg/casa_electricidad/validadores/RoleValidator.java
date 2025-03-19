package com.egg.casa_electricidad.validadores;

import java.util.Arrays;

import com.egg.casa_electricidad.constraints.ValidRole;
import com.egg.casa_electricidad.enumeraciones.Rol;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, Rol> {
  @Override
  public boolean isValid(Rol rol, ConstraintValidatorContext context) {
    // Validate that role is either ADMIN or USER
    return rol != null && Arrays.asList(Rol.ADMIN, Rol.USER).contains(rol);
  }
}