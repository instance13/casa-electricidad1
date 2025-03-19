package com.egg.casa_electricidad.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.egg.casa_electricidad.entidades.NroArticulo;

import jakarta.persistence.LockModeType;

@Repository
public interface NroArticuloRepositorio extends JpaRepository<NroArticulo, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT n FROM NroArticulo n ORDER BY n.id DESC LIMIT 1")
  Optional<NroArticulo> findLatestCounter();
}
