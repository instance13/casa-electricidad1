package com.egg.casa_electricidad.entidades;

import java.util.UUID;

import com.egg.casa_electricidad.enumeraciones.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  private String email;

  // @NotBlank(message = "El nombre no debe estar vacío.")
  private String nombre;

  // @NotBlank(message = "El apellido no debe estar vacío.")
  private String apellido;


  private String password;

  // @NotNull
  @Enumerated(EnumType.STRING)

  private Rol rol;

  @ManyToOne
  @JoinColumn(name = "idImagen")
  // @NotNull
  private Imagen imagenUsuario;
}
