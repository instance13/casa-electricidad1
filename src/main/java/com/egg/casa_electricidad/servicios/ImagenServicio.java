package com.egg.casa_electricidad.servicios;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egg.casa_electricidad.entidades.Imagen;
import com.egg.casa_electricidad.excepciones.ResourceNotFoundException;
import com.egg.casa_electricidad.repositorios.ImagenRepositorio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagenServicio {
  private final ImagenRepositorio imagenRepositorio;

  /**
   * Guarda una imagen en la base de datos
   *
   * @param archivo MultipartFile que contiene la imagen
   * @return La imagen guardada
   * @throws IllegalArgumentException Si el archivo es nulo o vacío
   */
  @Transactional
  public Imagen guardarImagen(MultipartFile archivo) {
    if (archivo == null || archivo.isEmpty()) {
      throw new IllegalArgumentException("El archivo de imagen no puede estar vacío");
    }

    try {
      Imagen imagen = Imagen.builder()
          .nombreImagen(archivo.getOriginalFilename())
          .mimeImagen(archivo.getContentType())
          .contenidoImagen(archivo.getBytes())
          .build();

      return imagenRepositorio.save(imagen);
    } catch (Exception e) {
      throw new RuntimeException("Error al guardar la imagen", e);
    }
  }

  /**
   * Obtiene una imagen por su ID
   *
   * @param idImagen UUID de la imagen
   * @return La imagen encontrada
   * @throws ResourceNotFoundException Si la imagen no existe
   */
  public Imagen obtenerImagenPorId(UUID idImagen) {
    return imagenRepositorio.findById(idImagen)
        .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada con ID: " + idImagen));
  }

  /**
   * Actualiza una imagen existente
   *
   * @param idImagen UUID de la imagen a actualizar
   * @param archivo  MultipartFile con la nueva imagen
   * @return La imagen actualizada
   * @throws ResourceNotFoundException Si la imagen no existe
   */
  @Transactional
  public Imagen actualizarImagen(UUID idImagen, MultipartFile archivo) {
    Imagen imagen = obtenerImagenPorId(idImagen);
    try {
      imagen.setNombreImagen(archivo.getOriginalFilename());
      imagen.setMimeImagen(archivo.getContentType());
      imagen.setContenidoImagen(archivo.getBytes());

      return imagenRepositorio.save(imagen);
    } catch (Exception e) {
      throw new RuntimeException("Error al actualizar la imagen", e);
    }
  }

  /**
   * Elimina una imagen por su ID
   *
   * @param idImagen UUID de la imagen a eliminar
   * @throws ResourceNotFoundException Si la imagen no existe
   */
  @Transactional
  public void eliminarImagen(UUID idImagen) {
    if (!imagenRepositorio.existsById(idImagen)) {
      throw new ResourceNotFoundException("Imagen no encontrada con ID: " + idImagen);
    }
    imagenRepositorio.deleteById(idImagen);
  }
}
