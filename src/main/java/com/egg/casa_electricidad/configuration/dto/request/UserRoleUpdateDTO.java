package com.egg.casa_electricidad.configuration.dto.request;

import java.util.UUID;

import com.egg.casa_electricidad.enumeraciones.Rol;

public record UserRoleUpdateDTO(UUID userId, Rol role) {
}
