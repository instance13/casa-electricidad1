package com.egg.casa_electricidad.configuration.dto.request;

import com.egg.casa_electricidad.entidades.Imagen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String email;
    private String nombre;
    private String apellido;
    private Imagen imagenUsuario;
}
