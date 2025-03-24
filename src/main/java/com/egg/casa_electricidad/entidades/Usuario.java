package com.egg.casa_electricidad.entidades;

import java.util.UUID;

import com.egg.casa_electricidad.constraints.ValidRole;
import com.egg.casa_electricidad.enumeraciones.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-args constructor (required by JPA)
@AllArgsConstructor // Generates a constructor with all args
@Builder // Implements the Builder pattern for this class
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID idUsuario;

  @NotBlank(message = "El email no debe estar vacío.")
  @Email(message = "Email inválido.")
  private String email;

  // @NotBlank(message = "El nombre no debe estar vacío.")
  private String nombre;

  // @NotBlank(message = "El apellido no debe estar vacío.")
  private String apellido;

  @NotBlank(message = "La contraseña no debe estar vacía.")
  @Size(min = 8, max = 20, message = "La contraseña debe estar entre 8 y 20 caracteres.")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", 
    message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
  private String password;

  // @NotNull
  @Enumerated(EnumType.STRING)
  @ValidRole
  private Rol rol;

  @ManyToOne
  @JoinColumn(name = "idImagen")
  // @NotNull
  private Imagen imagenUsuario;
}
