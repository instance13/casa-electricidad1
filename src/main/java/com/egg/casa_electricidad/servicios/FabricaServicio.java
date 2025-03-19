package com.egg.casa_electricidad.servicios;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.egg.casa_electricidad.entidades.Fabrica;
import com.egg.casa_electricidad.repositorios.FabricaRepositorio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FabricaServicio {
  private final FabricaRepositorio fabricaRepositorio;

  /**
   * Creates a new factory
   * 
   * @param nombre The name of the factory
   * @return The created factory
   */
  @Transactional
  public Fabrica createFabrica(String nombre) {
    Fabrica fabrica = Fabrica.builder()
        .nombreFabrica(nombre)
        .build();

    return fabricaRepositorio.save(fabrica);
  }

  /**
   * Creates a new factory with all required properties
   * 
   * @param fabrica The factory to create
   * @return The created factory
   */
  @Transactional
  public Fabrica createFabrica(Fabrica fabrica) {
    return fabricaRepositorio.save(fabrica);
  }

  /**
   * Retrieves all factories
   * 
   * @return List of all factories
   */
  public List<Fabrica> getAllFabricas() {
    return fabricaRepositorio.findAll();
  }

  /**
   * Finds a factory by its ID
   * 
   * @param id The UUID of the factory
   * @return The factory if found
   * @throws ResourceNotFoundException if the factory is not found
   */
  public Fabrica getFabricaById(UUID id) {
    return fabricaRepositorio.findById(id)
        .orElseThrow(() -> new com.egg.casa_electricidad.excepciones.ResourceNotFoundException(
            "Fábrica no encontrada con ID: " + id));
  }

  /**
   * Finds a factory by its name
   * 
   * @param nombre The name of the factory
   * @return The factory if found, empty Optional otherwise
   */
  public Optional<Fabrica> getFabricaByNombre(String nombre) {
    return fabricaRepositorio.findByNombreFabrica(nombre);
  }

  /**
   * Updates an existing factory
   * 
   * @param id             The UUID of the factory to update
   * @param fabricaDetails The updated factory details
   * @return The updated factory
   * @throws ResourceNotFoundException if the factory is not found
   */
  @Transactional
  public Fabrica updateFabrica(UUID id, Fabrica fabricaDetails) {
    Fabrica fabrica = getFabricaById(id);

    // Update fields
    fabrica.setNombreFabrica(fabricaDetails.getNombreFabrica());

    return fabricaRepositorio.save(fabrica);
  }

  /**
   * Deletes a factory by its ID
   * 
   * @param id The UUID of the factory to delete
   * @throws ResourceNotFoundException if the factory is not found
   */
  @Transactional
  public void deleteFabrica(UUID id) {
    if (!fabricaRepositorio.existsById(id)) {
      throw new com.egg.casa_electricidad.excepciones.ResourceNotFoundException("Fábrica no encontrada con ID: " + id);
    }
    fabricaRepositorio.deleteById(id);
  }

  /**
   * Checks if a factory exists by name
   * 
   * @param nombre The name to check
   * @return true if a factory with the given name exists, false otherwise
   */
  public boolean existsByNombre(String nombre) {
    return fabricaRepositorio.existsByNombreFabrica(nombre);
  }
}