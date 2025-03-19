package com.egg.casa_electricidad.servicios;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.egg.casa_electricidad.entidades.Usuario;
import com.egg.casa_electricidad.excepciones.ResourceNotFoundException;
import com.egg.casa_electricidad.repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioServicio implements UserDetailsService {
  private UsuarioRepositorio usuarioRepositorio;

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
  public List<Usuario> listarTodos() {
    return usuarioRepositorio.findAll();
  }

  /**
   * Finds a user by ID
   * 
   * @param id The UUID of the user
   * @return The user if found
   * @throws ResourceNotFoundException if the user is not found
   */
  public Usuario obtenerPorId(UUID id) {
    return usuarioRepositorio.findById(id)
        .orElseThrow(() -> new com.egg.casa_electricidad.excepciones.ResourceNotFoundException(
            "Usuario no encontrado con ID: " + id));
  }

  /**
   * Creates a new user
   * 
   * @param usuario The user to create
   * @return The created user
   */
  @Transactional
  public Usuario crearUsuario(Usuario usuario) {
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
  public Usuario actualizarUsuario(UUID id, Usuario userDetails) {
    Usuario usuario = obtenerPorId(id);

    usuario.setEmail(userDetails.getEmail());
    usuario.setNombre(userDetails.getNombre());
    usuario.setApellido(userDetails.getApellido());
    usuario.setPassword(userDetails.getPassword());
    usuario.setRol(userDetails.getRol());
    usuario.setImagenUsuario(userDetails.getImagenUsuario());

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
      throw new com.egg.casa_electricidad.excepciones.ResourceNotFoundException("Usuario no encontrado con ID: " + id);
    }
    usuarioRepositorio.deleteById(id);
  }
}
