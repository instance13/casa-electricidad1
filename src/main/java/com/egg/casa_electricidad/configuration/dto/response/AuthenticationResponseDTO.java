package com.egg.casa_electricidad.configuration.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationResponseDTO {
  private String token;

  public String getToken() {
    return token;
  }
}
