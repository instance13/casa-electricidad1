package com.egg.casa_electricidad.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nro_articulo")
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-args constructor (required by JPA)
@AllArgsConstructor // Generates a constructor with all args
@Builder // Implements the Builder pattern for this class: custom instantiation
public class NroArticulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrementing ID
    private Long id;

    @NotNull
    private Integer contador;
}
