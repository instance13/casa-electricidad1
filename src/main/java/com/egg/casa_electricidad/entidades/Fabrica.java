package com.egg.casa_electricidad.entidades;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fabrica")
@Data               // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor  // Generates a no-args constructor (required by JPA)
@AllArgsConstructor // Generates a constructor with all args
@Builder            // Implements the Builder pattern for this class
public class Fabrica {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID idFabrica;
  @NotBlank(message = "El nombre no debe estar vacío.")
  private String nombreFabrica;
}
