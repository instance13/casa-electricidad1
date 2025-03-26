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
import com.egg.casa_electricidad.configuration.dto.request.SuperAdminRequestDTO;
import com.egg.casa_electricidad.configuration.dto.request.UserRoleDTO;
import com.egg.casa_electricidad.configuration.dto.request.UserUpdateDTO;
import com.egg.casa_electricidad.configuration.dto.response.UserResponseDTO;
import com.egg.casa_electricidad.entidades.Usuario;
import com.egg.casa_electricidad.enumeraciones.Rol;
import com.egg.casa_electricidad.excepciones.ResourceNotFoundException;
import com.egg.casa_electricidad.excepciones.UnauthorizedException;
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

    String rawPassword = "admin123/*/";
    boolean isMatch = passwordEncoder.matches(rawPassword, usuario.getPassword());
    System.out.println("--- [!] Matches? " + isMatch);
    System.out.printf("Retrieved hashed password: %s\n", usuario.getPassword());

    System.out.println("-------- Verifying authentication details...");
    System.out.println("-------- Email: " + usuario.getEmail());
    System.out.println("-------- Stored Password Hash: " + usuario.getPassword());
    System.out.println("-------- Entered Password Matches? " + passwordEncoder.matches("admin123/*/", usuario.getPassword()));

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
   * Finds a user by email
   * 
   * @param email The email of the user
   * @return The user if found
   * @throws ResourceNotFoundException if the user is not found
   */
  public UserResponseDTO obtenerPorEmail(String email) {
    Usuario usuario = usuarioRepositorio.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Usuario no encontrado con email: " + email));

    return modelMapper.map(usuario, UserResponseDTO.class);
  }

  /**
   * Finds a user by email
   * 
   * @param email The email of the user
   * @return The user if found
   * @throws ResourceNotFoundException if the user is not found
   */
  public UserRoleDTO obtenerRolPorEmail(String email) {
    Usuario usuario = usuarioRepositorio.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Usuario no encontrado con email: " + email));

    return modelMapper.map(usuario, UserRoleDTO.class);
  }

  /**
   * Finds a user by email
   * 
   * @param email The email of the user
   * @return The user if found
   * @throws ResourceNotFoundException if the user is not found
   */
  public UserUpdateDTO obtenerPorEmailParaCambioDeRol(String email) {
    Usuario usuario = usuarioRepositorio.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Usuario no encontrado con email: " + email));

    return modelMapper.map(usuario, UserUpdateDTO.class);
  }

  /**
   * Finds a user by role
   * 
   * @param role The role to find
   * @return true if at least one user has the specified role
   */
  public boolean existeUsuarioPorRol(Rol role) {
    return usuarioRepositorio.existsByRol(role);
  }

  /**
   * Creates a new user with the role USER by default
   * 
   * @param usuario The user to create
   * @return The created user
   */
  @Transactional
  public Usuario crear(RegisterRequestDTO registerRequestDTO) {
    usuarioRepositorio.findByEmail(registerRequestDTO.getEmail())
        .ifPresent(user -> {
          throw new RuntimeException("El email ya está registrado.");
        });

    Usuario usuario = modelMapper.map(registerRequestDTO, Usuario.class);
    usuario.setRol(Rol.USER);
    
    usuario.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

    return usuarioRepositorio.save(usuario);
  }

  /**
   * Creates a new admin
   * 
   * @param usuario The user to create
   * @return The created user
   */
  @Transactional
  public Usuario crear(SuperAdminRequestDTO superAdminRequestDTO) {
    usuarioRepositorio.findByEmail(superAdminRequestDTO.getEmail())
        .ifPresent(user -> {
          throw new RuntimeException("El email ya está registrado.");
        });

    Usuario usuario = modelMapper.map(superAdminRequestDTO, Usuario.class);
    System.out.println("BEFORE hashing: " + superAdminRequestDTO.getPassword());
    usuario.setPassword(passwordEncoder.encode(superAdminRequestDTO.getPassword()));
    System.out.println("AFTER hashing: " + usuario.getPassword());

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
   * Updates an existing user
   * 
   *
   */
  @Transactional
  public UserRoleDTO actualizarRol(UUID userId, String nuevoRol, UserRoleDTO admin) {

    if (admin.getRole() != Rol.ADMIN) {
      throw new UnauthorizedException();
    }

    Usuario usuario = usuarioRepositorio.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));

    // there is no need to validate the role, since custom bean valdation constraint
    // does that in the UserRoleUpdatDTO :D.

    Rol nuevoRolEnum = Rol.valueOf(nuevoRol.toUpperCase());

    usuario.setRol(nuevoRolEnum);
    usuarioRepositorio.save(usuario);

    return modelMapper.map(usuario, UserRoleDTO.class);
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
