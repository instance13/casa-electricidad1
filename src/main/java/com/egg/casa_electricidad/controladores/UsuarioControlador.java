package com.egg.casa_electricidad.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egg.casa_electricidad.configuration.dto.request.RegisterRequestDTO;
import com.egg.casa_electricidad.configuration.dto.response.UserResponseDTO;
import com.egg.casa_electricidad.entidades.Usuario;
import com.egg.casa_electricidad.servicios.UsuarioServicio;

import lombok.RequiredArgsConstructor;

// i use rest controller since i'll be testing my api with postman, and i'm too lazy to create views myself :p.
// this returns json or xml instead of html
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioControlador {
  private final UsuarioServicio usuarioServicio;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserResponseDTO> listarUsuarios() {
    return usuarioServicio.listarTodos();

  }

  @PostMapping("/registro")
  public ResponseEntity<UserResponseDTO> registrarUsuario(@RequestBody RegisterRequestDTO registerRequestDTO) {
    Usuario usuario = usuarioServicio.registrar(registerRequestDTO);
    UserResponseDTO responseDTO = new UserResponseDTO(
        usuario.getIdUsuario(), usuario.getEmail(), usuario.getNombre(), usuario.getApellido(), usuario.getImagenUsuario());
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }
}
