package com.egg.casa_electricidad.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.casa_electricidad.entidades.Usuario;
import com.egg.casa_electricidad.enumeraciones.Rol;
import com.egg.casa_electricidad.servicios.UsuarioServicio;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioControlador {
  private final UsuarioServicio usuarioServicio;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public String listarUsuarios(Model model) {
    model.addAttribute("usuarios", usuarioServicio.listarTodos());
    return "usuarios/lista";
  }

  @GetMapping("/registro")
  public String registrarUsuario(Model model) {
    model.addAttribute("usuario", new Usuario());
    model.addAttribute("roles", Rol.values());
    return "usuarios/formulario-registro";
  }
}
