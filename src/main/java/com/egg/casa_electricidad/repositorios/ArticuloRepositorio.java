package com.egg.casa_electricidad.repositorios;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.casa_electricidad.entidades.Articulo;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, UUID> {
  public Optional<Articulo> findByNroArticulo(Integer nroArticulo);
  
  public Optional<Articulo> findByNombreArticulo(String nroArticulo);

  public boolean existsByNombreArticulo(String nombre);
}

