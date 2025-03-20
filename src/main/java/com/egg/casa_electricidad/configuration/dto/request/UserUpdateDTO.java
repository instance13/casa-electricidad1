package com.egg.casa_electricidad.configuration.dto.request;

import com.egg.casa_electricidad.entidades.Imagen;

public record UserUpdateDTO(
    String email,
    String nombre,
    String apellido,
    Imagen imagenUsuario) {
}