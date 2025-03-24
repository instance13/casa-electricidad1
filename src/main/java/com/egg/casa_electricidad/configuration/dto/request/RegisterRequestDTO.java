package com.egg.casa_electricidad.configuration.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-args constructor (required by JPA)
@AllArgsConstructor // Generates a constructor with all args
@Builder // Implements the Builder pattern for this class
public class RegisterRequestDTO {

    @Email
    @NotBlank(message = "El email no debe estar vacío.")
    private String email;

    @NotBlank(message = "La contraseña no debe estar vacía.")
    @Size(min = 8, max = 20, message = "La contraseña debe estar entre 8 y 20 caracteres.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
    private String password;
}