package com.egg.casa_electricidad.repositorios;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.casa_electricidad.entidades.Fabrica;

@Repository
public interface FabricaRepositorio extends JpaRepository<Fabrica, UUID> {
  public Optional<Fabrica> findByNombreFabrica(String nombre);
  public boolean existsByNombreFabrica(String nombre);
}
