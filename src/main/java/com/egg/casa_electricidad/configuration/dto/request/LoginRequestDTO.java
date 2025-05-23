package com.egg.casa_electricidad.configuration.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
    @NotBlank(message = "El email no debe estar vacío.") String email,

    @NotBlank(message = "La contraseña no debe estar vacía.") @Size(min = 8, max = 20, message = "La contraseña debe estar entre 8 y 20 caracteres.") String password) {
}
