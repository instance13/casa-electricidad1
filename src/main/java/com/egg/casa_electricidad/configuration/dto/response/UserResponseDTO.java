package com.egg.casa_electricidad.configuration.dto.response;

import java.util.UUID;

import com.egg.casa_electricidad.entidades.Imagen;

public record UserResponseDTO(
    UUID usuarioId,
    String email,
    String nombre,
    String apellido,
    Imagen imagenUsuario) {
}
