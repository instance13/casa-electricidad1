package com.egg.casa_electricidad.servicios;

import org.springframework.stereotype.Service;

import com.egg.casa_electricidad.entidades.NroArticulo;
import com.egg.casa_electricidad.repositorios.NroArticuloRepositorio;

import jakarta.transaction.Transactional;

@Service
public class NroArticuloServicio {
  private NroArticuloRepositorio nroArticuloRepositorio;

  public NroArticuloServicio(NroArticuloRepositorio nroArticuloRepositorio) {
    this.nroArticuloRepositorio = nroArticuloRepositorio;
  }

  @Transactional
  public int getNextArticuloNumber() { 
    NroArticulo latest = nroArticuloRepositorio.findLatestCounter()
        .orElseGet(() -> {
          NroArticulo newCounter = new NroArticulo();
          newCounter.setContador(1);
          return nroArticuloRepositorio.save(newCounter);
        });


    latest.setContador(latest.getContador() + 1);
    nroArticuloRepositorio.save(latest);

    return latest.getContador(); 
  }
}
