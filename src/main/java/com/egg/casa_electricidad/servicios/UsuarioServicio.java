package com.egg.casa_electricidad.servicios;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.egg.casa_electricidad.configuration.dto.request.RegisterRequestDTO;
import com.egg.casa_electricidad.configuration.dto.request.UserUpdateDTO;
import com.egg.casa_electricidad.configuration.dto.response.UserResponseDTO;
import com.egg.casa_electricidad.entidades.Usuario;
import com.egg.casa_electricidad.enumeraciones.Rol;
import com.egg.casa_electricidad.excepciones.ResourceNotFoundException;
import com.egg.casa_electricidad.repositorios.UsuarioRepositorio;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServicio implements UserDetailsService {
  private UsuarioRepositorio usuarioRepositorio;
  private ModelMapper modelMapper;
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepositorio.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));

    // using UserDetails implementation User from Spring!
    return new User(
        usuario.getEmail(),
        usuario.getPassword(),
        List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())));
  }

  /**
   * Retrieves all users
   * 
   * @return List of all users
   */
  // i need to analyze this code:
  public List<UserResponseDTO> listarTodos() {
    return usuarioRepositorio.findAll()
        .stream()
        .map(user -> modelMapper.map(user, UserResponseDTO.class))
        .toList();
  }

  /**
   * Finds a user by ID
   * 
   * @param id The UUID of the user
   * @return The user if found
   * @throws ResourceNotFoundException if the user is not found
   */
  public UserResponseDTO obtenerPorId(UUID id) {
    Usuario usuario = usuarioRepositorio.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Usuario no encontrado con ID: " + id));

    return modelMapper.map(usuario, UserResponseDTO.class);
  }

  /**
   * Creates a new user
   * 
   * @param usuario The user to create
   * @return The created user
   */
  @Transactional
  public Usuario crear(RegisterRequestDTO registerRequestDTO) {
    usuarioRepositorio.findByEmail(registerRequestDTO.email())
        .ifPresent(user -> {
          throw new RuntimeException("El email ya está registrado.");
        });

    Usuario usuario = modelMapper.map(registerRequestDTO, Usuario.class);
    usuario.setRol(Rol.USER);
    usuario.setPassword(passwordEncoder.encode(registerRequestDTO.password()));

    return usuarioRepositorio.save(usuario);
  }

  /**
   * Updates an existing user
   * 
   * @param id          The UUID of the user to update
   * @param userDetails The updated user details
   * @return The updated user
   * @throws ResourceNotFoundException if the user is not found
   */
  @Transactional
  public Usuario actualizarUsuario(UUID id, UserUpdateDTO userUpdateDTO) {
    Usuario usuario = usuarioRepositorio.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));

    modelMapper.getConfiguration().setSkipNullEnabled(true);
    modelMapper.map(userUpdateDTO, usuario);

    return usuarioRepositorio.save(usuario);
  }

  /**
   * Deletes a user by ID
   * 
   * @param id The UUID of the user to delete
   * @throws ResourceNotFoundException if the user is not found
   */
  @Transactional
  public void eliminarUsuario(UUID id) {
    if (!usuarioRepositorio.existsById(id)) {
      throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
    }
    usuarioRepositorio.deleteById(id);
  }
}
