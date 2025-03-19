package com.egg.casa_electricidad.servicios;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.egg.casa_electricidad.entidades.Articulo;
import com.egg.casa_electricidad.repositorios.ArticuloRepositorio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// i don't do validations in this service classes, since i'm relaying on bean validation, and for this project i consider bean validations to be enough.

@Service
@RequiredArgsConstructor
public class ArticuloServicio {
  private final ArticuloRepositorio articuloRepositorio;
  private final NroArticuloServicio nroArticuloServicio;

  /**
   * Creates a new article with the specified name
   * 
   * @param nombre The name of the article
   * @return The created article
   */
  @Transactional
  public Articulo createArticulo(String nombre) {
    int nextNumber = nroArticuloServicio.getNextArticuloNumber();

    // using lombok builder instead of setters just for fun hehe
    Articulo articulo = Articulo.builder()
        .nroArticulo(nextNumber)
        .nombreArticulo(nombre)
        .build();

    return articuloRepositorio.save(articulo);
  }

  /**
   * Creates a new article with all required properties
   * 
   * @param articulo The article to create
   * @return The created article
   */
  @Transactional
  public Articulo createArticulo(Articulo articulo) {
    int nextNumber = nroArticuloServicio.getNextArticuloNumber();
    articulo.setNroArticulo(nextNumber);

    return articuloRepositorio.save(articulo);
  }

  /**
   * Retrieves all articles
   * 
   * @return List of all articles
   */
  public List<Articulo> getAllArticulos() {
    return articuloRepositorio.findAll();
  }

  /**
   * Finds an article by its ID
   * 
   * @param id The UUID of the article
   * @return The article if found
   * @throws ResourceNotFoundException if the article is not found
   */
  public Articulo getArticuloById(UUID id) {
    return articuloRepositorio.findById(id)
        .orElseThrow(() -> new com.egg.casa_electricidad.excepciones.ResourceNotFoundException("Artículo no encontrado con ID: " + id));
  }

  /**
   * Finds an article by its article number
   * 
   * @param nroArticulo The article number
   * @return The article if found, empty Optional otherwise
   */
  public Optional<Articulo> getArticuloByNroArticulo(Integer nroArticulo) {
    return articuloRepositorio.findByNroArticulo(nroArticulo);
  }

  /**
   * Finds an article by its name
   * 
   * @param nombre The name of the article
   * @return The article if found, empty Optional otherwise
   */
  public Optional<Articulo> getArticuloByNombre(String nombre) {
    return articuloRepositorio.findByNombreArticulo(nombre);
  }

  /**
   * Updates an existing article
   * 
   * @param id              The UUID of the article to update
   * @param articuloDetails The updated article details
   * @return The updated article
   * @throws ResourceNotFoundException if the article is not found
   */
  @Transactional
  public Articulo updateArticulo(UUID id, Articulo articuloDetails) {
    Articulo articulo = getArticuloById(id);

    // Update fields (preserving nroArticulo which should not change)
    Integer nroArticulo = articulo.getNroArticulo();
    articulo.setNombreArticulo(articuloDetails.getNombreArticulo());
    articulo.setDescripcionArticulo(articuloDetails.getDescripcionArticulo());
    articulo.setFabrica(articuloDetails.getFabrica());
    articulo.setImagenArticulo(articuloDetails.getImagenArticulo());
    articulo.setNroArticulo(nroArticulo); // Ensure nroArticulo remains unchanged

    return articuloRepositorio.save(articulo);
  }

  /**
   * Deletes an article by its ID
   * 
   * @param id The UUID of the article to delete
   * @throws ResourceNotFoundException if the article is not found
   */
  @Transactional
  public void deleteArticulo(UUID id) {
    if (!articuloRepositorio.existsById(id)) {
      throw new com.egg.casa_electricidad.excepciones.ResourceNotFoundException("Artículo no encontrado con ID: " + id);
    }
    articuloRepositorio.deleteById(id);
  }

  /**
   * Checks if an article exists by name
   * 
   * @param nombre The name to check
   * @return true if an article with the given name exists, false otherwise
   */
  public boolean existsByNombre(String nombre) {
    return articuloRepositorio.existsByNombreArticulo(nombre);
  }
}