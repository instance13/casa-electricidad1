package com.egg.casa_electricidad.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.casa_electricidad.entidades.Imagen;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, UUID> {
  
}
