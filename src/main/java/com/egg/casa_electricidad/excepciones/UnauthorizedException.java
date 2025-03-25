package com.egg.casa_electricidad.excepciones;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException() {
    super("No tienes permiso para cambiar roles.");
  }
}
