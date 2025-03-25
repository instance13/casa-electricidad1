package com.egg.casa_electricidad.configuration.dto.request;

import com.egg.casa_electricidad.constraints.ValidRole;
import com.egg.casa_electricidad.enumeraciones.Rol;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-args constructor (required by JPA)
@AllArgsConstructor // Generates a constructor with all args
@Builder // Implements the Builder pattern for this class
public class UserRoleDTO {
  @NotNull
  @ValidRole
  Rol role;
}
