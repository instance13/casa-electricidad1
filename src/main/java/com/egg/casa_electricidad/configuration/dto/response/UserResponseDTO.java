package com.egg.casa_electricidad.configuration.dto.response;

import java.util.UUID;

import com.egg.casa_electricidad.entidades.Imagen;
import com.egg.casa_electricidad.enumeraciones.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private UUID idUsuario;
    private String email;
    private String nombre;
    private String apellido;
    private Rol rol;
    private Imagen imagenUsuario;
}
