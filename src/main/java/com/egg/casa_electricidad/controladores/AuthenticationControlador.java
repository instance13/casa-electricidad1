package com.egg.casa_electricidad.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egg.casa_electricidad.configuration.dto.request.AuthenticationRequestDTO;
import com.egg.casa_electricidad.configuration.dto.response.AuthenticationResponseDTO;
import com.egg.casa_electricidad.configuration.dto.response.UserResponseDTO;
import com.egg.casa_electricidad.entidades.Usuario;
import com.egg.casa_electricidad.servicios.UsuarioServicio;
import com.egg.casa_electricidad.util.JwtUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationControlador {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UsuarioServicio usuarioServicio;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> registrar(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
    Usuario usuario = usuarioServicio.crear(authenticationRequestDTO);
    UserResponseDTO responseDTO = new UserResponseDTO(
        usuario.getIdUsuario(), usuario.getEmail(), usuario.getNombre(), usuario.getApellido(), usuario.getRol(),
        usuario.getImagenUsuario());
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // generate JWT token
        String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());

        return ResponseEntity.ok(new AuthenticationResponseDTO(token));
    }
}

