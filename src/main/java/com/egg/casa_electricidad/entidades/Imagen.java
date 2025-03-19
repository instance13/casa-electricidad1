package com.egg.casa_electricidad.entidades;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagen {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID idImagen;

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
  private String nombreImagen;

  @NotBlank(message = "El tipo MIME es obligatorio")
  @Pattern(regexp = "^image/(jpeg|png|gif|bmp|svg\\+xml)$", message = "Formato de imagen no válido, debe ser JPEG, PNG, GIF, BMP o SVG")
  private String mimeImagen;

  @Lob
  @Column(columnDefinition = "LONGBLOB")
  @NotNull(message = "El contenido de la imagen es obligatorio")
  @Size(max = 5 * 1024 * 1024, message = "El tamaño de la imagen no puede exceder 5MB")
  private byte[] contenidoImagen;
}