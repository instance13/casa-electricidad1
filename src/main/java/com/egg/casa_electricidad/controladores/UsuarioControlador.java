
package com.egg.casa_electricidad.controladores;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egg.casa_electricidad.configuration.dto.request.RegisterRequestDTO;
import com.egg.casa_electricidad.configuration.dto.request.UserRoleDTO;
import com.egg.casa_electricidad.configuration.dto.request.UserUpdateDTO;
import com.egg.casa_electricidad.configuration.dto.response.UserResponseDTO;
import com.egg.casa_electricidad.entidades.Usuario;
import com.egg.casa_electricidad.servicios.UsuarioServicio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// i use rest controller since i'll be testing my api with postman, and i'm too lazy to create views myself :p.
// this returns json or xml instead of html

@Slf4j
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioControlador {
  private final UsuarioServicio usuarioServicio;
  private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> listar() {
    List<UserResponseDTO> usuarios = usuarioServicio.listarTodos();

    if (usuarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(usuarios);
  }

  // change: from /registro to default controller path in order to satisfy this
  // rule: CRUD function names should not be used in URIs.
  @PostMapping
  public ResponseEntity<UserResponseDTO> registrar(@RequestBody RegisterRequestDTO registerRequestDTO) {
    Usuario usuario = usuarioServicio.crear(registerRequestDTO);
    UserResponseDTO responseDTO = new UserResponseDTO(
        usuario.getIdUsuario(), usuario.getEmail(), usuario.getNombre(), usuario.getApellido(), usuario.getRol(),
        usuario.getImagenUsuario());
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> obtenerPorId(@PathVariable UUID id) {
    logger.debug("Received request for user ID: {}", id); //
    UserResponseDTO responseDTO = usuarioServicio.obtenerPorId(id);
    return ResponseEntity.ok(responseDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> actualizarDatosAdicionales(@PathVariable UUID id,
      @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
    Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(id, userUpdateDTO);

    UserResponseDTO responseDTO = new UserResponseDTO(
        usuarioActualizado.getIdUsuario(),
        usuarioActualizado.getEmail(),
        usuarioActualizado.getNombre(),
        usuarioActualizado.getApellido(),
        usuarioActualizado.getRol(),
        usuarioActualizado.getImagenUsuario());

    return ResponseEntity.ok(responseDTO);
  }

  @PutMapping("/{id}/rol")
  
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> actualizarRol(@PathVariable UUID id, @RequestParam String nuevoRol,
      Authentication authentication) {
    System.out.println("----------- Authentication object: " + authentication.getAuthorities());
    UserRoleDTO admin = usuarioServicio.obtenerRolPorEmail(authentication.getName()); // Get logged-in admin
    usuarioServicio.actualizarRol(id, nuevoRol, admin);
    return ResponseEntity.ok("Rol actualizado con éxito.");
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
    usuarioServicio.eliminarUsuario(id);
    return ResponseEntity.noContent().build();
  }

  // si quieres un método más claro o descriptivo en postman, descomenta este
  // código:
  /*
   * @DeleteMapping
   * public ResponseEntity<String> eliminar(@PathVariable UUID id) {
   * usuarioServicio.eliminarUsuario(id);
   * return ResponseEntity.ok("Usuario eliminado correctamente");
   * }
   */
}
