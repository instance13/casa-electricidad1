package com.egg.casa_electricidad.repositorios;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.casa_electricidad.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {
  public Optional<Usuario> findByEmail(String email);
}
